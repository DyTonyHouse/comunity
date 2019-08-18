package com.maurice.community.service;

import com.maurice.community.advice.CustomizeExceptionHandler;
import com.maurice.community.dto.PaginationDTO;
import com.maurice.community.dto.QuestionDTO;
import com.maurice.community.entity.Question;
import com.maurice.community.entity.QuestionExample;
import com.maurice.community.entity.User;
import com.maurice.community.exception.CustomizeErrorCode;
import com.maurice.community.exception.CustomizeException;
import com.maurice.community.exception.ICustomizeErrorCode;
import com.maurice.community.mapper.QuestionMapper;
import org.apache.ibatis.session.RowBounds;
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

        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount, currentPage, size);

        Integer offset = (paginationDTO.getCurrentPage() - 1) * size;

        RowBounds rowBounds = new RowBounds(offset,size);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), rowBounds);

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
        return paginationDTO;
    };


    public PaginationDTO myList(User user, Integer currentPage, Integer size) {
        //问题总个数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andUserIdEqualTo(user.getAccessId());

        Integer totalCount = (int) questionMapper.countByExample(questionExample);

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount, currentPage, size);

        Integer offset = (paginationDTO.getCurrentPage() - 1) * size;

        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setAvatarUrl(user.getAvatarUrl());
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        return paginationDTO;
    }



    public Question getByQuestionId(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        return question;
    }

    public void createOrUpdate(Integer id, Question question, User user) {
        if (id == null){
            question.setUserId(user.getAccessId());
            question.setGmtCreate(System.currentTimeMillis());
            int isUpdate = questionMapper.insert(question);
            if (isUpdate != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }else {
            Question dbQuestion = questionMapper.selectByPrimaryKey(id);
            dbQuestion.setTitle(question.getTitle());
            dbQuestion.setDescribtion(question.getDescribtion());
            dbQuestion.setTags(question.getTags());
            dbQuestion.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(id);
            int isUpdate = questionMapper.updateByExample(dbQuestion,questionExample);
            if (isUpdate != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
}
