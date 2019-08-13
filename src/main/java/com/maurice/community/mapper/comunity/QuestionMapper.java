package com.maurice.community.mapper.comunity;

import com.maurice.community.entity.Question;
import org.springframework.stereotype.Repository;

/**
 * @Author: Maurice
 * @Date: 2019/8/13
 * @Description:
 */
@Repository
public interface QuestionMapper {
    void insertQuestion(Question question);
}
