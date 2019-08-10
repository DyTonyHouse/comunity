package com.maurice.community.entity;

import lombok.Data;

/**
 * @Author: Maurice
 * @Date: 2019/8/10
 * @Description:
 */
@Data
public class AccessToken {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
