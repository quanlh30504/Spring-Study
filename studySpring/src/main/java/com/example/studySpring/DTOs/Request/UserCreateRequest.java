package com.example.studySpring.DTOs.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.validation.annotation.Validated;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @JsonProperty("fullName")
    @NotBlank(message = "Name cannot be left blank")
    private String fullName;
    @JsonProperty("phoneNumber")
    @Size(max = 10)
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("dob")
    private Date dateOfBirth;

    @JsonProperty("username")
    @Size(min = 3, message = "USERNAME_INVALID")
    private String username;

    @JsonProperty("password")
    @Size(min = 8,message = "PASSWORD_INVALID")
    private String password;
}
