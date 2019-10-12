package com.lyc.community.mapper;

import com.lyc.community.dto.QuestionQueryDTO;
import com.lyc.community.model.Question;
import com.lyc.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionMapperExtension {
    int increaseViewCount(Long id);
    int increaseCommentCount(Long id);

    int countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
