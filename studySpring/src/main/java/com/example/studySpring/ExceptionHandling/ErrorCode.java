package com.example.studySpring.ExceptionHandling;

public enum ErrorCode {
    USER_EXISTED(1001, "User existed"),
    USER_ID_NOT_EXISTED(1002,"UserId not existed"),
    USERNAME_NOT_EXISTED(1003, "Username not existed"),
    PRODUCT_ID_NOT_EXISTED(2002,"Product id not existed"),
    CATEGORY_ID_NOT_EXISTED(3002,"Category id not existed"),

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    INVALID_KEY(9998,"Invalid key message"),
    USERNAME_INVALID(9001,"Username must be at least 3 characters"),
    PASSWORD_INVALID(9002, "Password must be at least 8 characters"),
    UNAUTHENTICATED(9003, "Unauthenticated")
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
