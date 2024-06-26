package com.example.studySpring.Service;

import com.example.studySpring.DTOs.Request.AuthenticationRequest;
import com.example.studySpring.DTOs.Request.IntrospectRequest;
import com.example.studySpring.DTOs.Response.ApiResponse;
import com.example.studySpring.DTOs.Response.AuthenticationResponse;
import com.example.studySpring.DTOs.Response.IntrospectResponse;
import com.example.studySpring.ExceptionHandling.AppException;
import com.example.studySpring.ExceptionHandling.ErrorCode;
import com.example.studySpring.Models.User;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.StringJoiner;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    // Hàm xác thực đăng nhập bằng username, password
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED));
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
                .issuer("devteria.com") // xác nhận token dc gửi từ ai
                .issueTime(new Date())   // tời gian gửi token
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
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
    // Tạo scope role
    private String buildScope (User user){
        StringJoiner stringJoiner = new StringJoiner(" "); // Các role cách nhau bằng 1 khoảng trắng " "
//        if (!CollectionUtils.isEmpty(user.getRoles())) {
//            user.getRoles().forEach(role -> stringJoiner.add(role));
//        }
        return stringJoiner.toString();
    }

    // Kiểm tra tính hợp lệ của token
    public IntrospectResponse introspect (IntrospectRequest request)
            throws JOSEException, ParseException {
        String token = request.getToken();

        // vì dùng thuật toán HMAC để mã hóa signature -> dùng MACVerifier
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(verifier);
        Date expireTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponse.builder()
                .valid(verified && expireTime.after(new Date()))
                .build();
    }
}
