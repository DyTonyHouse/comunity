package com.maurice.community.controller;

import com.maurice.community.entity.User;
import com.maurice.community.mapper.comunity.UserMapper;
import com.maurice.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Maurice
 * @Date: 2019/8/10
 * @Description:
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(HttpServletResponse servletResponse){
//        User user = new User();
//        user.setAccessId("sd");
//        user.setName("da");
//        user.setToken("345");
//        user.setGmtCreate(System.currentTimeMillis());
//        user.setGmtModified(System.currentTimeMillis());
//        userService.insertUser(user);
        return "index";
    }

}
