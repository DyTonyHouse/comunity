package com.maurice.community.exception;

/**
 * @Author: Maurice
 * @Date: 2019/8/18
 * @Description:
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(Integer code, String massage){
        this.code = code;
        this.message = massage;
    }

    public CustomizeException(ICustomizeErrorCode ErrorCode){
        this.message = ErrorCode.getMessage();
        this.code = ErrorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode(){
        return code;
    }



}
