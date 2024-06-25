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
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ApiResponse<List<UserResponse>> getAllUser(){
        List<UserResponse> users = userService.getAllUser();
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Successfully get all user");
        apiResponse.setData(users);
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable int id){
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Successfully get user id + " + id)
                .data(userService.getUserById(id))
                .build();
        return apiResponse;
    }

    @PostMapping("/signUp")
    public UserCreateRequest createUser(@Valid @RequestBody UserCreateRequest newUser){
        return userService.createUser(newUser);
    }

}
