package com.txwl.txwlplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txwl.txwlplatform.model.entity.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ExamMapper extends BaseMapper<Exam> {
    
    /**
     * 获取所有考试，按年份倒序排列
     */
    @Select("SELECT * FROM exam ORDER BY exam_year DESC, id DESC")
    List<Exam> selectAllOrderByYearDesc();

    /**
     * 获取指定年份范围内的高考考试ID列表
     */
    @Select("SELECT id FROM exam WHERE exam_year >= #{startYear} AND exam_year <= #{endYear} " +
            "AND exam_name = '高考' ORDER BY exam_year DESC")
    List<Long> selectGaokaoExamIdsByYear(@Param("startYear") Integer startYear,
                                   @Param("endYear") Integer endYear);
}