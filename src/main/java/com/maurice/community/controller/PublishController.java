package com.maurice.community.controller;

import com.maurice.community.entity.Question;
import com.maurice.community.entity.User;
import com.maurice.community.service.QuestionService;
import com.maurice.community.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String publish(Model model){
        Question question = new Question();
        model.addAttribute("question", question);

        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable(name = "id") Integer id,
                               Model model){
        Question question = questionService.getByQuestionId(id);
        model.addAttribute("question", question);
        return "publish";
    }

    @PostMapping("/publish")
    public String publish(@RequestParam(value = "title", required = false) String title,
                          @RequestParam(value = "describtion", required = false) String describition,
                          @RequestParam(value = "tags", required = false) String tags,
                          @RequestParam(value = "id", required = false) Integer id,
                          HttpServletRequest servletRequest,
                          Model model){

        Question question = new Question();
        question.setTitle(title);
        question.setDescribtion(describition);
        question.setTags(tags);
        model.addAttribute("question", question);
        if (title == null || title == ""){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (describition == null || describition == ""){
            model.addAttribute("error", "说明不能为空");
            return "publish";
        }
        if (tags == null || tags == ""){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = (User) servletRequest.getSession().getAttribute("user");

        question.setGmtModified(System.currentTimeMillis());
        question.setUserId(user.getAccessId());
        questionService.createOrUpdate(id, question, user);

        return "redirect:/";
    }
}
