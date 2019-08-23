package com.maurice.community.dto;

import com.maurice.community.entity.User;
import lombok.Data;

/**
 * @Author: Maurice
 * @Date: 2019/8/21
 * @Description:
 */
@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId;
    private String accessId;
    private Integer type;
    private String content;
    private Long likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
