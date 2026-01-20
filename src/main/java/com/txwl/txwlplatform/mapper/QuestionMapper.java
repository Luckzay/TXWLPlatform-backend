package com.txwl.txwlplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txwl.txwlplatform.model.entity.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}