package com.maurice.community.service;

import com.maurice.community.entity.User;
import com.maurice.community.mapper.comunity.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Maurice
 * @Date: 2019/8/11
 * @Description:
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void insertUser(User user){
        userMapper.insertUser(user);
    }
}
