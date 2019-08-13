package com.maurice.community.service;

import com.maurice.community.entity.Question;
import com.maurice.community.mapper.comunity.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Maurice
 * @Date: 2019/8/13
 * @Description:
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    public void insertQuestion(Question question){
        questionMapper.insertQuestion(question);
    }
}
