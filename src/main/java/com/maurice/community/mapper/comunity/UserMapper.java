package com.maurice.community.mapper.comunity;

import com.maurice.community.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author: Maurice
 * @Date: 2019/8/11
 * @Description:
 */
@Repository
public interface UserMapper {
    void insertUser(User user);
}
