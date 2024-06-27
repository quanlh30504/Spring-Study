package com.example.studySpring.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserUpdateRequest {
    private String password;
    private String fullName;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    List<String> roles;

}
