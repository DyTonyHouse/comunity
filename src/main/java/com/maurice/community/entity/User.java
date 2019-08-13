package com.maurice.community.entity;

import lombok.Data;


@Data
public class User {
    private Integer id;
    private String accessId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
