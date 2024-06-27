package com.example.studySpring.ExceptionHandling;

import com.example.studySpring.DTOs.Response.ApiResponse;
import org.apache.http.protocol.ResponseServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlingException(Exception exception){
        ApiResponse apiResponse = new ApiResponse<>();


        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> hanldingValidation(MethodArgumentNotValidException exception){
        List<ApiResponse> apiResponses = new ArrayList<>();

        // handling từ exception
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String enumCode = error.getDefaultMessage();
            ErrorCode errorCode = ErrorCode.INVALID_KEY;

            // Bắt exception truyền sai error code
            try {
                errorCode = ErrorCode.valueOf(enumCode);
            } catch (IllegalArgumentException e){

            }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessage(errorCode.getMessage());
            apiResponses.add(apiResponse);
        });
//        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponses);
    }

    @ExceptionHandler(value = org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handlingAccessDeniedException(org.springframework.security.access.AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }
}
