package com.maurice.community.exception;

/**
 * @Author: Maurice
 * @Date: 2019/8/18
 * @Description:
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "您找的问题不存在，换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中问题或者评论进行回复。"),
    TYPE_PARAM_WRONG(2003, "回复类型传参有异常！"),
    SYSTEM_ERROR(2004, "服务器太热啦，要不换个请求试试？"),
    COMMENT_NOT_FOUND(2005, "请选定问题回复！"),
    NOT_LOGIN(2006, "当前操作需要登陆，请登陆后重试！");


    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
