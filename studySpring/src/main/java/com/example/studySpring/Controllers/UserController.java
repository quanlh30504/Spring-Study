package com.example.studySpring.Controllers;
import com.example.studySpring.DTOs.Request.UserCreateRequest;
import com.example.studySpring.DTOs.Response.ApiResponse;
import com.example.studySpring.DTOs.Response.UserResponse;
import com.example.studySpring.Models.User;
import com.example.studySpring.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ApiResponse<List<UserResponse>> getAllUser(){
        List<UserResponse> users = userService.getAllUser();
        return ApiResponse.<List<UserResponse>>builder()
                .message("Successfully get all user" )
                .data(users)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Successfully get user" )
                .data(userService.getUserById(id))
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Successfully get info!")
                .data(userService.getMyInfo())
                .build();
    }

    @PostMapping("/signUp")
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreateRequest newUser){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Create user successfully!")
                .data(userService.createUser(newUser))
                .build();
    }

}
