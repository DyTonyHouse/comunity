package com.maurice.community.utils;

import com.maurice.community.entity.User;
import com.maurice.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Maurice
 * @Date: 2019/8/13
 * @Description:
 */

public class Httpd {

    public static String getToken(HttpServletRequest servletRequest){
        Cookie[] cookies = servletRequest.getCookies();

        String token = null;
        if (cookies != null && cookies.length>0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }
}
