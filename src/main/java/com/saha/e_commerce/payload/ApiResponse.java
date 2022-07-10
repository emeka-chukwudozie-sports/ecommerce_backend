package com.saha.e_commerce.payload;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApiResponse {
    private final boolean success;
    private final String message;
//    private final int code;


    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
//        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

//    public int getCode() {
//        return code;
//    }

    public String getTimeStamp(){
        return LocalDateTime.now().toString();
    }
}
