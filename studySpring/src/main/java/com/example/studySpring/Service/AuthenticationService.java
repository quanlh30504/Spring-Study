package com.example.studySpring.Service;

import com.example.studySpring.DTOs.Request.AuthenticationRequest;
import com.example.studySpring.DTOs.Response.ApiResponse;
import com.example.studySpring.DTOs.Response.AuthenticationResponse;
import com.example.studySpring.ExceptionHandling.AppException;
import com.example.studySpring.ExceptionHandling.ErrorCode;
import com.example.studySpring.Models.User;
import com.example.studySpring.Repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Data
@RequiredArgsConstructor
public class AuthenticationService {

    private Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @NonFinal
    private static final String SIGNER_KEY =
            "d3+MtWAFiL3bhiALgkfeMQeWFT3sa7krhWp/HLYh0SrB4jlXf0MKd8IDI4B6hDM1\n";

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generatedToken(request.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // Hàm tạo token
    public String generatedToken(String username){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // Data trong body là claim
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username) // claim này đại diện cho người đăng nhập
                .issuer("devteria.com") // xác nhận token dc issuer từ ai
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("customClaim","Custom")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (KeyLengthException e) {
            log.error("Cannot create token " + e);
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            log.error("Cannot create token " + e);
            throw new RuntimeException(e);
        }
        // tạo signature

    }
}
