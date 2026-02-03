package com.txwl.txwlplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.txwl.txwlplatform.model.entity.ScoreRank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ScoreRankMapper extends BaseMapper<ScoreRank> {
    
    /**
     * 根据考试ID和科目类型获取一分一段数据，按分数降序排列
     */
    @Select("SELECT * FROM score_rank WHERE exam_id = #{examId} AND subject_type = #{subjectType} ORDER BY score DESC")
    List<ScoreRank> selectByExamIdAndSubject(@Param("examId") Long examId, @Param("subjectType") String subjectType);

    /**
     * 根据考试ID列表获取一分一段数据
     */
    @Select("<script>" +
            "SELECT * FROM score_rank WHERE exam_id IN " +
            "<foreach collection='examIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            " AND subject_type = #{subjectType} " +
            "ORDER BY score DESC" +
            "</script>")
    List<ScoreRank> selectByExamIdsAndSubject(@Param("examIds") List<Long> examIds, 
                                             @Param("subjectType") String subjectType);

}