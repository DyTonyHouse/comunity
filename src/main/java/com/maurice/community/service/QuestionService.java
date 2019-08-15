package com.maurice.community.service;

import com.maurice.community.dto.PaginationDTO;
import com.maurice.community.dto.QuestionDTO;
import com.maurice.community.entity.Question;
import com.maurice.community.entity.User;
import com.maurice.community.mapper.comunity.QuestionMapper;
import com.maurice.community.mapper.comunity.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Maurice
 * @Date: 2019/8/13
 * @Description:
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserService userService;

    public PaginationDTO list(Integer currentPage, Integer size){
        //问题总个数
        Integer totalCount = questionMapper.getCount();

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount, currentPage, size);

        Integer offset = (paginationDTO.getCurrentPage() - 1) * size;

        List<Question> questionList = questionMapper.list(offset, size);

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questionList) {
            User user = userService.findByAccessId(question.getUserId());

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            if (user != null){
                questionDTO.setAvatarUrl(user.getAvatarUrl());
            }
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        System.out.println("paginationDTO.getPages():===="+paginationDTO.getPages());
        return paginationDTO;
    };

    public void insertQuestion(Question question){
        questionMapper.insertQuestion(question);
    }


    public PaginationDTO myList(User user, Integer currentPage, Integer size) {
        //问题总个数
        Integer totalCount = questionMapper.getMyCount(user.getAccessId());
        System.out.println("user.getAccessId()============="+user.getAccessId());
        System.out.println("totalCount============="+totalCount);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount, currentPage, size);

        Integer offset = (paginationDTO.getCurrentPage() - 1) * size;

        Map<String, Object> parm = new HashMap<>();
        parm.put("accessId", user.getAccessId());
        parm.put("offset", offset);
        parm.put("size", size);
        List<Question> questionList = questionMapper.myList(parm);
        System.out.println("questionList=========================="+questionList);
        System.out.println("questionList=========================="+questionList.size());
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setAvatarUrl(user.getAvatarUrl());
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        System.out.println("paginationDTO.getPages():===="+paginationDTO.getPages());
        return paginationDTO;
    }



    public Question getByQuestionId(String id) {
        Question question = questionMapper.getByQuestionId(id);
        return question;
    }
}
