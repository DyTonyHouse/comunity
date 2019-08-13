package com.maurice.community.controller;

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
    public String index(HttpServletRequest servletRequest,
                        Model model){
        String token = Httpd.getToken(servletRequest);
        if (token != null){
            User user = userService.findByToken(token);
            servletRequest.getSession().setAttribute("user",user);
        }

        List<Question> questionList = questionService.list();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questionList) {
            System.out.println("question:=============="+question);
            User user = userService.findByAccessId(question.getUserId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            System.out.println("user:=============="+user);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);

        }
        model.addAttribute("questions", questionDTOS);
        return "index";
    }



}
