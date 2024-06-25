package com.example.studySpring.Controllers;

import com.example.studySpring.DTOs.Request.AuthenticationRequest;
import com.example.studySpring.DTOs.Response.ApiResponse;
import com.example.studySpring.DTOs.Response.AuthenticationResponse;
import com.example.studySpring.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.http.nio.protocol.Pipelined;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @PostMapping("/signIn")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(authenticationResponse);
        return apiResponse;
    }

}
