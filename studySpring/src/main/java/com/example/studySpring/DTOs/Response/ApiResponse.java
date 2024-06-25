package com.example.studySpring.DTOs.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // ẩn các trường null khi hiển thi Json
public class ApiResponse <T> {
    private int code = 1000; // code khi thành công là 1000
    private String message;
    private T data;
}
