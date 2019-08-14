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
import java.util.List;

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
        System.out.println("totalCount:=========="+totalCount);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount, currentPage, size);

        Integer offest = (paginationDTO.getCurrentPage() - 1) * size;
        System.out.println("offest:=============="+offest);
        List<Question> questionList = questionMapper.list(offest, size);

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questionList) {
            User user = userService.findByAccessId(question.getUserId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        System.out.println("paginationDTO.getPages():===="+paginationDTO.getPages());
        return paginationDTO;
    };

    public void insertQuestion(Question question){
        questionMapper.insertQuestion(question);
    }


}
