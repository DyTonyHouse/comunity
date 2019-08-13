package com.maurice.community.controller;

import com.maurice.community.entity.Question;
import com.maurice.community.entity.User;
import com.maurice.community.service.QuestionService;
import com.maurice.community.service.UserService;
import com.maurice.community.utils.Httpd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Maurice
 * @Date: 2019/8/13
 * @Description:
 */
@Controller
public class PublishController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String publish(@RequestParam(value = "title") String title,
                          @RequestParam(value = "describtion") String describiton,
                          @RequestParam(value = "tags") String tags,
                          HttpServletRequest servletRequest,
                          Model model){
        model.addAttribute("title", title);
        model.addAttribute("describiton", describiton);
        model.addAttribute("tags", tags);

        if (title == null || title == ""){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (describiton == null || describiton == ""){
            model.addAttribute("error", "说明不能为空");
            return "publish";
        }
        if (tags == null || tags == ""){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        String token = Httpd.getToken(servletRequest);
        User user = userService.findByToken(token);
        Question question = new Question();
        question.setUserId(user.getAccessId());
        question.setTitle(title);
        question.setDescribtion(describiton);
        question.setTags(tags);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionService.insertQuestion(question);
        return "redirect:/";
    }
}
