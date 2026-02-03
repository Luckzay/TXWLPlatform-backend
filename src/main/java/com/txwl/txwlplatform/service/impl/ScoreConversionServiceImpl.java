package com.txwl.txwlplatform.service.impl;

import com.txwl.txwlplatform.mapper.ExamMapper;
import com.txwl.txwlplatform.mapper.ScoreLineMapper;
import com.txwl.txwlplatform.mapper.ScoreRankMapper;
import com.txwl.txwlplatform.model.dto.ExamListDto;
import com.txwl.txwlplatform.model.dto.ScoreConversionRequestDto;
import com.txwl.txwlplatform.model.dto.ScoreConversionResultDto;
import com.txwl.txwlplatform.model.entity.Exam;
import com.txwl.txwlplatform.model.entity.ScoreLine;
import com.txwl.txwlplatform.model.entity.ScoreRank;
import com.txwl.txwlplatform.service.IScoreConversionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreConversionServiceImpl implements IScoreConversionService {
    
    private final ExamMapper examMapper;
    private final ScoreRankMapper scoreRankMapper;
    private final ScoreLineMapper scoreLineMapper;
    
    @Override
    public List<ExamListDto> getExamList() {
        List<Exam> exams = examMapper.selectAllOrderByYearDesc();
        return exams.stream()
                .map(exam -> new ExamListDto(exam.getId(), exam.getExamYear(), exam.getExamName()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ScoreConversionResultDto> convertScore(ScoreConversionRequestDto request) {
        List<ScoreConversionResultDto> results = new ArrayList<>();
        log.info("开始分数转换: examId={}, subjectType={}, score={}", 
                request.getExamId(), request.getSubjectType(), request.getScore());
        
        // 获取模考的一分一段数据
        List<ScoreRank> mockScoreRanks = scoreRankMapper.selectByExamIdAndSubject(
                request.getExamId(), request.getSubjectType());
        
        if (!mockScoreRanks.isEmpty()) {
            // 使用一分一段表方法进行转换
            results = convertByScoreRank(request, mockScoreRanks);
            return results.isEmpty()? results: null;
        }
        
        // 如果没有一分一段数据，尝试使用分数线数据
        ScoreLine mockScoreLine = scoreLineMapper.selectByExamIdAndSubject(
                request.getExamId(), request.getSubjectType());
        
        if (mockScoreLine != null) {
            // 使用分数线方法进行转换
            results = convertByScoreLine(request, mockScoreLine);
            return results.isEmpty()? results: null;
        }
        
        // 数据缺失，返回错误
        throw new RuntimeException("未查询到相关数据");
    }
    
    /**
     * 使用一分一段表进行转换
     */
    private List<ScoreConversionResultDto> convertByScoreRank(ScoreConversionRequestDto request, 
                                                              List<ScoreRank> mockScoreRanks) {
        // 找到输入分数对应的位次
        Integer inputScore = request.getScore();
        ScoreRank matchedRank = findMatchingRank(mockScoreRanks, inputScore);
        
        if (matchedRank == null) {
            // 分数超出范围的处理
            return handleOutOfRangeByScoreRank(request.getSubjectType(), inputScore, mockScoreRanks);
        }
        
        Integer rank = matchedRank.getRank();
        log.info("找到匹配位次: score={}, rank={}", inputScore, rank);
        
        // 按年份分别处理近三年高考的一分一段数据
        int currentYear = java.time.LocalDate.now().getYear();
        int startYear = currentYear - 3;
        
        return findConvertedScoresByYear(rank, request.getSubjectType(), startYear, currentYear);
    }
    
    /**
     * 使用分数线进行转换
     */
    private List<ScoreConversionResultDto> convertByScoreLine(ScoreConversionRequestDto request, 
                                                              ScoreLine mockScoreLine) {
        Integer inputScore = request.getScore();
        String subjectType = request.getSubjectType();
        
        // 判断分数属于哪个分数线区间
        ScoreLevelInfo levelInfo = determineScoreLevel(inputScore, mockScoreLine);
        
        if (levelInfo == null) {
            // 分数低于本科线的特殊处理
            return handleBelowBenkeScore(request, mockScoreLine);
        }
        
        // 计算分差
        int scoreDiff = inputScore - levelInfo.getBaseScore();
        log.info("分数差值: {} - {} = {}", inputScore, levelInfo.getBaseScore(), scoreDiff);
        
        // 按年份分别处理近三年高考的分数线
        int currentYear = java.time.LocalDate.now().getYear();
        int startYear = currentYear - 3;
        
        return calculateConvertedScoresByYear(levelInfo.getLineType(), scoreDiff, subjectType, startYear, currentYear);
    }
    
    /**
     * 查找匹配的位次
     */
    private ScoreRank findMatchingRank(List<ScoreRank> scoreRanks, Integer inputScore) {
        // 从高分到低分查找
        for (ScoreRank rank : scoreRanks) {
            if (inputScore.equals(rank.getScore()) ) {
                return rank;
            }
        }
        return null;
    }
    
    /**
     * 处理超出一分一段表范围的情况
     */
    private List<ScoreConversionResultDto> handleOutOfRangeByScoreRank(String subjectType, 
                                                                       Integer inputScore, 
                                                                       List<ScoreRank> mockScoreRanks) {
        List<ScoreConversionResultDto> results = new ArrayList<>();
        int currentYear = java.time.LocalDate.now().getYear();
        int startYear = currentYear - 2;
        
        // 获取最高分和最低分记录
        ScoreRank highest = mockScoreRanks.get(0);  // 分数最高的记录
        ScoreRank lowest = mockScoreRanks.get(mockScoreRanks.size() - 1);  // 分数最低的记录
        
        String desc;
        if (inputScore > highest.getScore()) {
            // 高于最高分：给出上限估计
            desc = "超过" + highest.getScore() + "分，排名预计小于" + highest.getRank() + "名（顶尖水平）";
        } else if (inputScore < lowest.getScore()) {
            // 低于最低分：给出下限估计
            desc = "低于" + lowest.getScore() + "分，排名预计大于" + lowest.getRank() + "名（需要努力）";
        } else {
            // 理论上不应该到达这里
            desc = "分数范围异常，请检查数据";
        }
        
        // 为近三年都添加相同的描述
        for (int year = currentYear; year >= startYear; year--) {
            results.add(new ScoreConversionResultDto(year, "高考", null, desc));
        }
        
        return results;
    }
    
    /**
     * 按年份分别查找转换后的分数
     */
    private List<ScoreConversionResultDto> findConvertedScoresByYear(Integer targetRank,
                                                                     String subjectType,
                                                                     int startYear,
                                                                     int endYear) {
        List<ScoreConversionResultDto> results = new ArrayList<>();
        List<Long> gaokaoExamIds = examMapper.selectGaokaoExamIdsByYear(startYear, endYear);
        List<ScoreRank> gaokaoScoreRanks = scoreRankMapper.selectByExamIdsAndSubject(gaokaoExamIds, subjectType);
        Map<Long, List<ScoreRank>> yearScoreRanksMap = gaokaoScoreRanks.stream()
                .collect(Collectors.groupingBy(ScoreRank::getExamId));
        yearScoreRanksMap.forEach((examId, scoreRanks) -> {
            // 在该年份的数据中查找最接近的位次
            ScoreRank convertedRank = findClosestRankByRank(scoreRanks, targetRank);
            if (convertedRank != null) {
                String desc = convertedRank.getScore() + "分，" + convertedRank.getRank() + "名";
                results.add(new ScoreConversionResultDto(examMapper.selectById(examId).getExamYear(), "高考", convertedRank.getScore(), desc));
            }
        });
        // 按年份降序排列
        results.sort((r1, r2) -> Integer.compare(r2.getYear(), r1.getYear()));
        return results;
    }
    
    /**
     * 根据位次查找最接近的记录
     */
    private ScoreRank findClosestRankByRank(List<ScoreRank> scoreRanks, Integer targetRank) {
        ScoreRank closest = null;
        int minDiff = Integer.MAX_VALUE;
        
        for (ScoreRank rank : scoreRanks) {
            int diff = Math.abs(rank.getRank() - targetRank);
            if (diff < minDiff) {
                minDiff = diff;
                closest = rank;
            }
        }
        
        return closest;
    }
    
    /**
     * 分数线等级信息
     */
    private static class ScoreLevelInfo {
        private String lineType;  // qingbei, 985, 211, tekong, benke
        private int baseScore;    // 基准分数线
        
        public ScoreLevelInfo(String lineType, int baseScore) {
            this.lineType = lineType;
            this.baseScore = baseScore;
        }
        
        public String getLineType() { return lineType; }
        public int getBaseScore() { return baseScore; }
    }
    
    /**
     * 判断分数属于哪个分数线区间
     */
    private ScoreLevelInfo determineScoreLevel(Integer score, ScoreLine scoreLine) {
        // 清北线
        if (scoreLine.getQingbeiScore() != null && score >= scoreLine.getQingbeiScore()) {
            return new ScoreLevelInfo("qingbei", scoreLine.getQingbeiScore());
        }
        // 985线
        if (scoreLine.getScore985() != null && score >= scoreLine.getScore985()) {
            return new ScoreLevelInfo("985", scoreLine.getScore985());
        }
        // 211线
        if (scoreLine.getScore211() != null && score >= scoreLine.getScore211()) {
            return new ScoreLevelInfo("211", scoreLine.getScore211());
        }
        // 特控线
        if (score >= scoreLine.getTekongScore()) {
            return new ScoreLevelInfo("tekong", scoreLine.getTekongScore());
        }
        // 本科线
        if (score >= scoreLine.getBenkeScore()) {
            return new ScoreLevelInfo("benke", scoreLine.getBenkeScore());
        }
        
        // 低于本科线返回null
        return null;
    }
    
    /**
     * 处理低于本科线的情况（优化版：避免连表查询）
     */
    private List<ScoreConversionResultDto> handleBelowBenkeScore(ScoreConversionRequestDto request, 
                                                                 ScoreLine mockScoreLine) {
        List<ScoreConversionResultDto> results = new ArrayList<>();
        int currentYear = java.time.LocalDate.now().getYear();
        int startYear = currentYear - 2;
        int scoreDiff = request.getScore() - mockScoreLine.getBenkeScore(); // 负数
        
        // 第一步：获取指定年份范围内的高考考试ID列表
        List<Long> examIds = scoreLineMapper.selectGaokaoExamIds(startYear, currentYear);
        if (examIds.isEmpty()) {
            return results;
        }
        
        // 第二步：批量获取所有相关的分数线数据
        List<ScoreLine> gaokaoScoreLines = scoreLineMapper.selectByExamIdsAndSubject(examIds, request.getSubjectType());
        if (gaokaoScoreLines.isEmpty()) {
            return results;
        }
        
        // 第三步：批量获取考试信息
        List<Exam> exams = examMapper.selectBatchIds(examIds);
        Map<Long, Exam> examMap = exams.stream().collect(Collectors.toMap(Exam::getId, exam -> exam));
        
        // 第四步：处理每个考试的分数线数据
        for (ScoreLine line : gaokaoScoreLines) {
            Exam exam = examMap.get(line.getExamId());
            if (exam != null) {
                int convertedScore = line.getBenkeScore() + scoreDiff;
                String desc = "低于本科线" + Math.abs(scoreDiff) + "分（需要继续努力）";
                results.add(new ScoreConversionResultDto(exam.getExamYear(), "高考", convertedScore, desc));
            }
        }
        
        // 按年份降序排列
        results.sort((r1, r2) -> Integer.compare(r2.getYear(), r1.getYear()));
        
        return results;
    }
    
    /**
     * 按年份分别计算转换分数
     */
    private List<ScoreConversionResultDto> calculateConvertedScoresByYear(String lineType,
                                                                          int scoreDiff,
                                                                          String subjectType,
                                                                          int startYear,
                                                                          int endYear) {
        List<ScoreConversionResultDto> results = new ArrayList<>();
        // 逐年处理，确保每一年都有独立的结果
        for (int year = endYear; year >= startYear; year--) {
            // 获取指定年份的高考考试ID
            List<Long> examIds = scoreLineMapper.selectGaokaoExamIds(year, year);
            List<ScoreLine> yearScoreLines = new ArrayList<>();
            if (!examIds.isEmpty()) {
                yearScoreLines = scoreLineMapper.selectByExamIdsAndSubject(examIds, subjectType);
            }
            
            if (!yearScoreLines.isEmpty()) {
                ScoreLine line = yearScoreLines.get(0); // 取第一条记录
                Integer baseScore = getBaseScoreByLineType(line, lineType);
                if (baseScore != null) {
                    int convertedScore = baseScore + scoreDiff;
                    String desc = getLineDescription(lineType) + "+" + scoreDiff + "分";
                    results.add(new ScoreConversionResultDto(year, "高考", convertedScore, desc));
                }
            }
        }
        
        return results;
    }
    
    /**
     * 根据年份查找分数线
     */
    private ScoreLine findScoreLineByYear(List<ScoreLine> scoreLines, int targetYear) {
        // 这里需要关联exam表获取年份，简化处理直接返回第一个
        return scoreLines.isEmpty() ? null : scoreLines.get(0);
    }
    
    /**
     * 根据分数线类型获取基准分数
     */
    private Integer getBaseScoreByLineType(ScoreLine scoreLine, String lineType) {
        switch (lineType) {
            case "qingbei": return scoreLine.getQingbeiScore();
            case "985": return scoreLine.getScore985();
            case "211": return scoreLine.getScore211();
            case "tekong": return scoreLine.getTekongScore();
            case "benke": return scoreLine.getBenkeScore();
            default: return null;
        }
    }
    
    /**
     * 获取分数线描述
     */
    private String getLineDescription(String lineType) {
        switch (lineType) {
            case "qingbei": return "清北线";
            case "985": return "985线";
            case "211": return "211线";
            case "tekong": return "特控线";
            case "benke": return "本科线";
            default: return "";
        }
    }
}