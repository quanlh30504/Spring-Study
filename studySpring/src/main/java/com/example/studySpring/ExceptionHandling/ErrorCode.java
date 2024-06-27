package com.example.studySpring.ExceptionHandling;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // User Exception
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USER_ID_NOT_EXISTED(1002,"UserId not existed",HttpStatus.NOT_FOUND),
    USERNAME_NOT_EXISTED(1003, "Username not existed", HttpStatus.NOT_FOUND),

    //Role
    ROLE_NOT_EXISTED(2003,"Role not existed",HttpStatus.NOT_FOUND),

    // Product exception
    PRODUCT_ID_NOT_EXISTED(3002,"Product id not existed",HttpStatus.NOT_FOUND),

    // Category exception
    CATEGORY_ID_NOT_EXISTED(4002,"Category id not existed",HttpStatus.NOT_FOUND),

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error",HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9998,"Invalid key message",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(9001,"Username must be at least 3 characters",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(9002, "Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(9003, "Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(9004, "You don't have permission", HttpStatus.FORBIDDEN)
    ;
    private int code;
    private String message;
    private HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
