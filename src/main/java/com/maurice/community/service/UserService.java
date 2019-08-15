package com.maurice.community.service;

import com.maurice.community.entity.GithubUser;
import com.maurice.community.entity.User;
import com.maurice.community.mapper.comunity.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    public User findByToken(String token) {
        User user = userMapper.findBytoken(token);
        return user;
    }

    public User findByAccessId(String userId) {
        User user = userMapper.findByAccessId(userId);
        return user;
    }

    public void createOrUpdate(User user, GithubUser githubUser, HttpServletResponse servletResponse, HttpServletRequest servletRequest) {
        if (user == null){
            user.setAccessId(githubUser.getId());
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.create(user);
        }else {
            user.setToken(UUID.randomUUID().toString());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.update(user);
        }
        //手动写入cookie
//        servletRequest.getSession().setAttribute("user", user);
        System.out.println("user.token11111111111111////////////"+user.getToken());
        servletResponse.addCookie(new Cookie("token", user.getToken()));

    }
}
