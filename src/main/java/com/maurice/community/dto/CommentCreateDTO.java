package com.maurice.community.dto;

import lombok.Data;

/**
 * @Author: Maurice
 * @Date: 2019/8/19
 * @Description:
 */
@Data
public class CommentCreateDTO {
    private Integer parentId;
    private Integer type;
    private String content;
    private String accessId;
}
