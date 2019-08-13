package com.maurice.community.entity;

import lombok.Data;

/**
 * @Author: Maurice
 * @Date: 2019/8/10
 * @Description:
 */
@Data
public class GithubUser {
    private String name;
    private String id;
    private String bio;
    private String avatar_url;
}
