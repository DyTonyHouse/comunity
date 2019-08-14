package com.maurice.community.controller;

import com.maurice.community.dto.PaginationDTO;
import com.maurice.community.dto.QuestionDTO;
import com.maurice.community.entity.Question;
import com.maurice.community.entity.User;
import com.maurice.community.mapper.comunity.UserMapper;
import com.maurice.community.service.QuestionService;
import com.maurice.community.service.UserService;
import com.maurice.community.utils.Httpd;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Maurice
 * @Date: 2019/8/10
 * @Description:
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "1") Integer currentPage,
                        @RequestParam(name = "size", defaultValue = "2") Integer size,
                        HttpServletRequest servletRequest,
                        Model model){
        System.out.println("currentPage:============="+currentPage);
        System.out.println("size:============="+size);
        String token = Httpd.getToken(servletRequest);
        if (token != null){
            User user = userService.findByToken(token);
            servletRequest.getSession().setAttribute("user",user);
        }

        PaginationDTO paginationDTO = questionService.list(currentPage, size);

        model.addAttribute("questions", paginationDTO);
        return "index";
    }



}
