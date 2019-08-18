package com.maurice.community.exception;

/**
 * @Author: Maurice
 * @Date: 2019/8/18
 * @Description:
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(String massage){
        this.message = massage;
    }

    public CustomizeException(ICustomizeErrorCode code){
        this.message = code.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
