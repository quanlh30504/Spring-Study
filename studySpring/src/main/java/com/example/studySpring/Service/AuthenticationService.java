package com.example.studySpring.Service;

import com.example.studySpring.DTOs.Request.AuthenticationRequest;
import com.example.studySpring.DTOs.Request.IntrospectRequest;
import com.example.studySpring.DTOs.Request.LogoutRequest;
import com.example.studySpring.DTOs.Request.RefreshTokenRequest;
import com.example.studySpring.DTOs.Response.ApiResponse;
import com.example.studySpring.DTOs.Response.AuthenticationResponse;
import com.example.studySpring.DTOs.Response.IntrospectResponse;
import com.example.studySpring.ExceptionHandling.AppException;
import com.example.studySpring.ExceptionHandling.ErrorCode;
import com.example.studySpring.Models.InvalidatedToken;
import com.example.studySpring.Models.User;
import com.example.studySpring.Repository.InvalidatedTokenRepository;
import com.example.studySpring.Repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.internal.ObjectNameSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Value("${jwt.valid-duration}")
    private long VALID_DURATION;

    @Value("${jwt.refreshable-duration}")
    private long REFRESH_DURATION;

    // Hàm xác thực đăng nhập bằng username, password
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generatedToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // Hàm tạo token
    public String generatedToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // Data trong body là claim
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername()) // claim này đại diện cho người đăng nhập
                .issuer("quannguyen.com") // xác nhận token dc gửi từ ai
                .issueTime(new Date())   // tời gian gửi token
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString()) //Thêm ID cho token
                .claim("scope",this.buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();  //tạo token
        } catch (JOSEException e) {
            log.error("Cannot create token " + e);
            throw new RuntimeException(e);
        }
    }
    // Tạo thêm thồn tin role và permission vào scope
    private String buildScope (User user){
        StringJoiner stringJoiner = new StringJoiner(" "); // Các role cách nhau bằng 1 khoảng trắng " "

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                // Thêm role vào scope
                stringJoiner.add(role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    //Thêm permission uẩ từng role vào scope
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }
        return stringJoiner.toString();
    }

    // Kiểm tra tính hợp lệ của token
    public IntrospectResponse introspect (IntrospectRequest request)
            throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e){
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public SignedJWT verifyToken(String token, boolean isRefresh)
            throws JOSEException, ParseException {
        // vì dùng thuật toán HMAC để mã hóa signature -> dùng MACVerifier
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(verifier);

        // Kiểm tra đây là token hay refresh token để gán thời gian hết hạn cho từng loại
        Date expireTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant()
                .plus(REFRESH_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        // Token ko đúng hoặc quá hạn (Tương tự với refresh token)
        if (!(verified && expireTime.after(new Date()))){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        //Không xác thực token nằm trong bảng Invalidated Token
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    //Logout
    public void logout(LogoutRequest request)
            throws ParseException, JOSEException {
        try{
            SignedJWT signToken = verifyToken(request.getToken(),false);

            //Lấy id và expiration của token
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();
            // Lưu token vào bàng Invalidated Token
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e){
            log.info("Token already is expired");
        }

    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        // Lưu token cũ vào bàng Invalidated Token
        invalidatedTokenRepository.save(invalidatedToken);

        String username = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        // Tạo token mới
        String token = generatedToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

}
