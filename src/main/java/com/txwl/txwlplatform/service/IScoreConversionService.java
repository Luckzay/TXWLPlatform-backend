package com.txwl.txwlplatform.service;

import com.txwl.txwlplatform.model.dto.ExamListDto;
import com.txwl.txwlplatform.model.dto.ScoreConversionRequestDto;
import com.txwl.txwlplatform.model.dto.ScoreConversionResultDto;
import java.util.List;

public interface IScoreConversionService {
    
    /**
     * 获取所有考试列表，按年份倒序排列
     * @return 考试列表
     */
    List<ExamListDto> getExamList();
    
    /**
     * 分数转换主方法
     * @param request 转换请求参数
     * @return 转换结果列表（近三年高考数据）
     */
    List<ScoreConversionResultDto> convertScore(ScoreConversionRequestDto request);
}