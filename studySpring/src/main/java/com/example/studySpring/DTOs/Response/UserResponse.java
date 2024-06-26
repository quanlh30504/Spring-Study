package com.example.studySpring.DTOs.Response;

import com.example.studySpring.Models.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String fullName;
    String username;
    String phoneNumber;
    String address;
    Date dateOfBirth;
//    LocalDateTime created_at;
//    LocalDateTime updated_at;
    Set<RoleResponse> roles;

}
