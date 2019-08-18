package com.maurice.community.exception;

/**
 * @Author: Maurice
 * @Date: 2019/8/18
 * @Description:
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("您找的问题不存在，换个试试？");

    private String message;

    CustomizeErrorCode(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
