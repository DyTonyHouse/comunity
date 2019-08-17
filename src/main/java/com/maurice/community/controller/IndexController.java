package com.maurice.community.controller;

import com.maurice.community.dto.PaginationDTO;
import com.maurice.community.service.QuestionService;
import com.maurice.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "1") Integer currentPage,
                        @RequestParam(name = "size", defaultValue = "2") Integer size,
                        Model model){

        PaginationDTO paginationDTO = questionService.list(currentPage, size);

        model.addAttribute("questions", paginationDTO);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", "");
        response.addCookie(cookie);
        return "redirect:/";
    }
}
