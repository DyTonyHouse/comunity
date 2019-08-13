package com.maurice.community.entity;

import lombok.Data;

/**
 * @Author: Maurice
 * @Date: 2019/8/13
 * @Description:
 */
@Data
public class Question {
    private Integer id;
    private String userId;
    private String title;
    private String describtion;
    private String tags;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private String avatarUrl;
}
