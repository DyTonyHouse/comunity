package com.maurice.community.service;

import com.maurice.community.dto.QuestionDTO;
import com.maurice.community.entity.Question;
import com.maurice.community.mapper.comunity.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Question> list(){
        List<Question> questionList = questionMapper.list();
        return questionList;
    };

    public void insertQuestion(Question question){
        questionMapper.insertQuestion(question);
    }
}
