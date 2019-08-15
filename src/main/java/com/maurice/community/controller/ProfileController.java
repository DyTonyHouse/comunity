package com.maurice.community.controller;

import com.maurice.community.dto.PaginationDTO;
import com.maurice.community.entity.User;
import com.maurice.community.service.QuestionService;
import com.maurice.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Maurice
 * @Date: 2019/8/15
 * @Description:
 */
@Controller
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable (name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer currentPage,
                          @RequestParam(name = "size", defaultValue = "2") Integer size,
                          HttpServletRequest servletRequest, Model model
                          ){

        User user = (User)servletRequest.getSession().getAttribute("user");

        if (user == null){
            return "redirect:/";
        }

        PaginationDTO paginationDTO = questionService.myList(user, currentPage, size);

        if ("questions".equals(action)){
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "我的提问");
        }else if ("replies".equals(action)){
            model.addAttribute("section", action);
            model.addAttribute("sectionName", "最新回复");
        }
        model.addAttribute("questions", paginationDTO);

        return "profile";
    }
}
