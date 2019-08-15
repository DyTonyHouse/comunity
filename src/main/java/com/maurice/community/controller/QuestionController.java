package com.maurice.community.controller;

import com.maurice.community.entity.Question;
import com.maurice.community.entity.User;
import com.maurice.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Maurice
 * @Date: 2019/8/15
 * @Description:
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/question/{id}")
    public String Question(@PathVariable(name = "id") String id,
                           HttpServletRequest request,
                           Model model){
        User user = (User) request.getSession().getAttribute("user");
        Question question = questionService.getByQuestionId(id);
        model.addAttribute("question", question);
        model.addAttribute("user", user);
        return "question";
    }
}
