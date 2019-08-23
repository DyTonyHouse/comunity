package com.maurice.community.controller;

import com.maurice.community.dto.CommentDTO;
import com.maurice.community.dto.QuestionDTO;
import com.maurice.community.entity.Comment;
import com.maurice.community.entity.Question;
import com.maurice.community.entity.User;
import com.maurice.community.exception.CustomizeErrorCode;
import com.maurice.community.exception.CustomizeException;
import com.maurice.community.service.CommentService;
import com.maurice.community.service.QuestionService;
import com.maurice.community.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Maurice
 * @Date: 2019/8/15
 * @Description:
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String Question(@PathVariable(name = "id") Integer id,
                           HttpServletRequest request,
                           Model model) {
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getByQuestionId(id);
        String[] tags = StringUtils.split(questionDTO.getTags(),",");
        List<String> tagList = Arrays.asList(tags);
        List<CommentDTO> comments = commentService.getCommentList(questionDTO);

        model.addAttribute("question", questionDTO);
        model.addAttribute("tags", tagList);
        model.addAttribute("comments", comments);
        return "question";
    }
}
