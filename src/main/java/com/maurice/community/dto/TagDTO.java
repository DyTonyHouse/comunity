package com.maurice.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Maurice
 * @Date: 2019/8/23
 * @Description:
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
