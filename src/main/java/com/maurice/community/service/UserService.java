package com.maurice.community.service;

import com.maurice.community.entity.GithubUser;
import com.maurice.community.entity.User;
import com.maurice.community.entity.UserExample;
import com.maurice.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

    public User findByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0);
    }

    public User findByAccessId(String userId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccessIdEqualTo(userId);
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0);
    }

    public void createOrUpdate(User user, GithubUser githubUser, HttpServletResponse servletResponse) {
        if (user == null){
            user.setAccessId(githubUser.getId());
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user);
        }else {
            user.setToken(UUID.randomUUID().toString());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl(githubUser.getAvatar_url());

            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(user.getId());
            userMapper.updateByExampleSelective(user, userExample);
        }
        //手动写入cookie
//        servletRequest.getSession().setAttribute("user", user);
        servletResponse.addCookie(new Cookie("token", user.getToken()));

    }
}
