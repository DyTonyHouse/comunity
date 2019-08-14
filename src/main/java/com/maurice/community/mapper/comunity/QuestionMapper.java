package com.maurice.community.mapper.comunity;

import com.maurice.community.dto.QuestionDTO;
import com.maurice.community.entity.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Maurice
 * @Date: 2019/8/13
 * @Description:
 */
@Repository
public interface QuestionMapper {
    void insertQuestion(Question question);

    List<Question> list(Integer offset, Integer size);

    Integer getCount();
}
