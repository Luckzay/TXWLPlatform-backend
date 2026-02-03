package com.txwl.txwlplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txwl.txwlplatform.model.entity.ScoreLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ScoreLineMapper extends BaseMapper<ScoreLine> {
    
    /**
     * 根据考试ID和科目类型获取分数线数据
     */
    @Select("SELECT * FROM score_line WHERE exam_id = #{examId} AND subject_type = #{subjectType}")
    ScoreLine selectByExamIdAndSubject(@Param("examId") Long examId, @Param("subjectType") String subjectType);
    
    /**
     * 根据考试年份、考试名称和科目类型获取分数线数据
     */
    @Select("SELECT sl.* FROM score_line sl JOIN exam e ON sl.exam_id = e.id " +
            "WHERE e.exam_year = #{year} AND e.exam_name = #{examName} AND sl.subject_type = #{subjectType}")
    ScoreLine selectByYearExamAndSubject(@Param("year") Integer year, @Param("examName") String examName, @Param("subjectType") String subjectType);
    
    /**
     * 根据考试ID列表获取分数线数据
     */
    @Select("<script>" +
            "SELECT * FROM score_line WHERE exam_id IN " +
            "<foreach collection='examIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            " AND subject_type = #{subjectType}" +
            "</script>")
    List<ScoreLine> selectByExamIdsAndSubject(@Param("examIds") List<Long> examIds, 
                                             @Param("subjectType") String subjectType);
    
    /**
     * 获取指定年份范围内的高考考试ID列表
     */
    @Select("SELECT id FROM exam WHERE exam_year >= #{startYear} AND exam_year <= #{endYear} " +
            "AND exam_name = '高考' ORDER BY exam_year DESC")
    List<Long> selectGaokaoExamIds(@Param("startYear") Integer startYear, 
                                  @Param("endYear") Integer endYear);
}