package com.maurice.community.mapper;

import com.maurice.community.entity.Question;
import com.maurice.community.entity.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionMapperEx {

    void incView(Question question);

    void incComment(Question dbQuestion);
}