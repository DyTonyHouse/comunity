package com.maurice.community.controller;

import com.maurice.community.cache.TagCache;
import com.maurice.community.dto.QuestionDTO;
import com.maurice.community.dto.TagDTO;
import com.maurice.community.entity.Question;
import com.maurice.community.entity.User;
import com.maurice.community.service.QuestionService;
import com.maurice.community.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @Autowired
    private TagCache tagCache;

    @GetMapping("/publish")
    public String publish(Model model){
        Question question = new Question();
        model.addAttribute("question", question);

        List<TagDTO> tags = tagCache.get();
        model.addAttribute("tags",tags);
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable(name = "id") Integer id,
                               Model model){
        QuestionDTO questionDTO = questionService.getByQuestionId(id);
        model.addAttribute("question", questionDTO);

        List<TagDTO> tags = tagCache.get();
        model.addAttribute("tags",tags);
        return "publish";
    }

    @PostMapping("/publish")
    public String publish(@RequestParam(value = "title", required = false) String title,
                          @RequestParam(value = "describtion", required = false) String describition,
                          @RequestParam(value = "tag", required = false) String tag,
                          @RequestParam(value = "id", required = false) Integer id,
                          HttpServletRequest servletRequest,
                          Model model){
        Question question = new Question();
        question.setTitle(title);
        question.setDescribtion(describition);
        question.setTags(tag);
        model.addAttribute("question", question);
        if (title == null || title == ""){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (describition == null || describition == ""){
            model.addAttribute("error", "说明不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        String invalid = tagCache.filterInvalid(tag);

        if (StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入非法标签"+invalid);
            return "publish";
        }

        User user = (User) servletRequest.getSession().getAttribute("user");
        questionService.createOrUpdate(id, question, user);

        return "redirect:/";
    }
}
