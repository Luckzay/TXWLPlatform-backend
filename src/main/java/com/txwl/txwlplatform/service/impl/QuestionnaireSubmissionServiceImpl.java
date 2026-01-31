package com.txwl.txwlplatform.service.impl;

import com.txwl.txwlplatform.mapper.ReportMapper;
import com.txwl.txwlplatform.mapper.RoleMapper;
import com.txwl.txwlplatform.model.entity.Question;
import com.txwl.txwlplatform.model.entity.Report;
import com.txwl.txwlplatform.model.entity.User;
import com.txwl.txwlplatform.service.IAiService;
import com.txwl.txwlplatform.service.IQuestionService;
import com.txwl.txwlplatform.service.IQuestionnaireSubmissionService;
import com.txwl.txwlplatform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.txwl.txwlplatform.interceptor.RequestLogInterceptor.objectMapper;

@Service
public class QuestionnaireSubmissionServiceImpl implements IQuestionnaireSubmissionService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private IAiService aiService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IUserService userService;
    @Autowired
    private RoleMapper roleMapper;



    @Override
    public String submitQuestionnaire(Long userId, Long paperId, Map<Long, Object> answers) {
        // 根据传入的userId（可能是手机号）查找真实的用户ID
        User user = userService.getUserById(userId);
        String userRole = roleMapper.selectById(user.getRoleId()).getRoleName();
        if (user == null) {
            throw new IllegalArgumentException("找不到用户: " + userId);
        }
        Long actualUserId = user.getUid(); // 使用数据库中的真实用户ID

        // 根据答案中的questionId获取对应的题目
        Set<Long> questionIds = answers.keySet();
        List<Question> questions = questionService.getQuestionsByIds(questionIds);

        // 将题目和答案配对
        Map<Question, Object> questionAnswerPairs = new HashMap<>();
        for (Question question : questions) {
            Object answer = answers.get(question.getId());
            questionAnswerPairs.put(question, answer);
        }

        // 调用AI服务生成分析报告（包含题目信息）
        String aiGeneratedReport = aiService.generateReportWithQuestions(questionAnswerPairs, userRole);
        System.out.println("AI生成的报告: " + aiGeneratedReport);

        // 解析AI返回的JSON数据
        JsonNode reportData;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            reportData = objectMapper.readTree(aiGeneratedReport);
        } catch (Exception e) {
            System.err.println("解析AI返回的JSON数据失败: " + e.getMessage());
            throw new RuntimeException("AI返回的数据格式错误");
        }

        // 生成报告文件（HTML格式）
        String reportUrl = generateHtmlReportFile(reportData, actualUserId, paperId);

        // 保存报告信息到数据库
        Report report = createReport(actualUserId, paperId, reportUrl);

        return reportUrl;
    }

    private Report createReport(Long userId, Long paperId, String reportUrl) {
        Report report = new Report();
        report.setUserId(userId);
        report.setPaperId(paperId);
        report.setUrl(reportUrl);
        report.setCreateTime(LocalDateTime.now());

        reportMapper.insert(report);
        return report;
    }

    private String generateHtmlReportFile(JsonNode reportData, Long userId, Long paperId) {
        try {
            // 创建报告存储目录
            String projectPath = System.getProperty("user.dir", "");
            if (projectPath.isEmpty()) {
                // 如果获取不到项目路径，使用相对路径
                projectPath = "./";
            }
            String basePath = projectPath + "/src/main/resources/static/reports/";
            String fileName = "report_" + userId + "_" + paperId + "_" + System.currentTimeMillis() + ".html";
            String fullPath = basePath + fileName;
            
            // 确保目录存在
            File directory = new File(basePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // 生成HTML报告
            generateHtmlReport(fullPath, reportData, userId, paperId);
            
            // 返回相对于应用的路径
            return "/reports/" + fileName;
        } catch (Exception e) {
            System.err.println("生成HTML报告文件失败: " + e.getMessage());
            e.printStackTrace();
            // 返回错误路径或抛出异常
            throw new RuntimeException("生成HTML报告文件失败", e);
        }
    }
    
    private void generateHtmlReport(String filePath, JsonNode reportData, Long userId, Long paperId) throws Exception {
        // 获取用户信息用于报告
        User user = userService.getUserById(userId);
        String userName = user != null ? user.getUsername() : "未知用户";
        String userPhone = user != null ? user.getPhone() : "未知手机号";
        
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html lang=\"zh-CN\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>生涯测评报告</title><style>");
        // CSS样式
        html.append("@page { size: A4; margin: 20mm; }\n");
        html.append("body { font-family: \\\"Source Han Sans CN\\\", \\\"思源黑体\\\", \\\"PingFang SC\\\", \\\"Microsoft YaHei\\\", sans-serif; font-size: 12pt; line-height: 1.5; color: #222; margin: 20px; padding: 20px; }\n"); // 添加了边距
        html.append(".page { page-break-after: always; position: relative; min-height: 297mm; margin: 20px auto; max-width: 210mm; padding: 20mm; box-sizing: border-box; }\n"); // 添加了页面边距和最大宽度
        html.append(".no-break { page-break-inside: avoid; }\n");
        html.append("h1 { font-size: 28pt; text-align: center; margin: 0 0 20mm 0; color: #0052D9; }\n");
        html.append("h2 { font-size: 18pt; text-align: left; margin: 20mm 0 10mm 0; color: #0052D9; }\n");
        html.append("h3 { font-size: 14pt; font-weight: bold; margin: 15mm 0 8mm 0; color: #222; }\n");
        html.append(".header-line { position: fixed; top: 8mm; left: 0; right: 0; height: 1px; background-color: #E4E7ED; }\n");
        html.append(".footer { position: fixed; bottom: 8mm; left: 0; right: 0; text-align: center; font-size: 9pt; color: #666; }\n");
        html.append(".page-number { text-align: center; }\n");
        html.append(".cover-info { margin: 30mm 0; text-align: center; }\n");
        html.append(".cover-info p { margin: 5mm 0; }\n");
        html.append(".toc-item { display: flex; justify-content: space-between; margin: 5mm 0; }\n");
        html.append(".rating-bar-container { width: 100%; max-width: 300px; height: 6px; background-color: #E4E7ED; margin: 5px 0; }\n");
        html.append(".rating-bar-fill { height: 100%; background-color: #0052D9; }\n");
        html.append(".trait-card { display: flex; align-items: center; margin: 10px 0; padding: 10px; border: 1px solid #E4E7ED; border-radius: 5px; }\n");
        html.append(".trait-name { width: 120px; font-weight: bold; }\n");
        html.append(".trait-details { flex-grow: 1; }\n");
        html.append(".trait-analysis { margin-top: 5px; font-size: 10pt; color: #555; }\n");
        html.append(".subject-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 10px; margin-top: 10px; }\n");
        html.append(".subject-card { padding: 10px; border: 1px solid #E4E7ED; border-radius: 5px; }\n");
        html.append(".dot { font-size: 12pt; margin-right: 8px; }\n");
        html.append(".summary { font-size: 20pt; font-weight: bold; text-align: center; margin: 40mm 0; color: #0052D9; }\n");
        html.append(".indent { text-indent: 2em; }\n");
        html.append(".chart-container { width: 300px; height: 300px; margin: 20px auto; }\n");
        html.append("</style>");
        // 添加Chart.js库
        html.append("<script src=\"https://cdn.jsdelivr.net/npm/chart.js@3.9.1/dist/chart.min.js\"></script></head><body>");
        
        // 封面页
        html.append("<div class='page'>");
        html.append("<div class='header-line'></div>");
        html.append("<h1>生涯测评报告</h1>");
        html.append("<div class='cover-info'>");
        html.append("<p>用户：").append(escapeHtmlContent(userName)).append("</p>");
        html.append("<p>评测人：同行未来生涯规划分析师</p>");
        html.append("<p>").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))).append("</p>");
        html.append("</div>");
        html.append("</div>");
        
        // 目录页
        html.append("<div class='page'>");
        html.append("<div class='header-line'></div>");
        html.append("<h2>目录</h2>");
        html.append("<div class='toc-item'><span>1. 职业特质</span><span>3</span></div>");
        html.append("<div class='toc-item'><span>2. 专业推荐</span><span>4</span></div>");
        html.append("<div class='toc-item'><span>3. 生涯规划</span><span>5</span></div>");
        html.append("</div>");
        
        // 职业特质页
        html.append("<div class='page'>");
        html.append("<div class='header-line'></div>");
        html.append("<h2>职业特质</h2>");
        
        // 霍兰德六型雷达图
        html.append("<h3>霍兰德六型</h3>");
        html.append("<div class='chart-container'><canvas id='holland-chart'></canvas></div>");
        
        // 霍兰德六型详情
        html.append("<div class='no-break'>");
        html.append(getTraitDetailsHtml(reportData, "career_traits.holland_types", new String[]{
            "realistic", "investigative", "artistic", "social", "enterprising", "conventional"}));
        html.append("</div>");
        
        // 五力详情
        html.append("<h3>五力</h3>");
        html.append("<div class='chart-container'><canvas id='five-powers-chart'></canvas></div>");
        html.append("<div class='no-break'>");
        html.append(getTraitDetailsHtml(reportData, "career_traits.five_powers", new String[]{
            "affinity", "driving_force", "execution", "stress_resistance", "planning"}));
        html.append("</div>");
        
        // 八型人格详情
        html.append("<h3>八型人格</h3>");
        html.append("<div class='chart-container'><canvas id='eight-personality-chart'></canvas></div>");
        html.append("<div class='no-break'>");
        html.append(getTraitDetailsHtml(reportData, "career_traits.eight_personality", new String[]{
            "perfectionist_dolphin", "lively_parrot", "brave_tiger", "achiever_lion", 
            "feeling_peacock", "thinker_owl", "peaceful_dove", "friendly_panda"}));
        html.append("</div>");
        
        html.append("</div>");
        
        // 专业推荐页
        html.append("<div class='page'>");
        html.append("<div class='header-line'></div>");
        html.append("<h2>专业推荐</h2>");
        
        // 十二学科雷达图
        html.append("<div class='chart-container'><canvas id='twelve-subjects-chart'></canvas></div>");
        
        // 十二学科详情
        html.append("<div class='subject-grid'>");
        html.append(getSubjectDetailsHtml(reportData));
        html.append("</div>");
        
        // 适合学科
        html.append("<h3>适合学科</h3>");
        html.append("<p class='indent'>").append(escapeHtmlContent(getStringValue(reportData, "major_recommendation.suitable_disciplines", "—"))).append("</p>");
        
        html.append("</div>");
        
        // 生涯规划页
        html.append("<div class='page'>");
        html.append("<div class='header-line'></div>");
        html.append("<h2>生涯规划</h2>");
        
        html.append("<p><span class='dot'>•</span><strong>兴趣特点：</strong>").append(escapeHtmlContent(getStringValue(reportData, "career_planning.interest_characteristics", "—"))).append("</p>");
        html.append("<p><span class='dot'>•</span><strong>能力性格：</strong>").append(escapeHtmlContent(getStringValue(reportData, "career_planning.ability_temperament", "—"))).append("</p>");
        html.append("<p><span class='dot'>•</span><strong>人格思维：</strong>").append(escapeHtmlContent(getStringValue(reportData, "career_planning.personality_thinking", "—"))).append("</p>");
        html.append("<p><span class='dot'>•</span><strong>天赋学科：</strong>").append(escapeHtmlContent(getStringValue(reportData, "career_planning.talent_subjects", "—"))).append("</p>");
        
        html.append("</div>");
        
        // 摘要页
        html.append("<div class='page'>");
        html.append("<div class='header-line'></div>");
        html.append("<h2>总体评价</h2>");
        html.append("<div class='summary'>").append(escapeHtmlContent(getStringValue(reportData, "career_traits.summary", "—"))).append("</div>");
        html.append("</div>");
        
        // 封底页
        html.append("<div class='page'>");
        html.append("<div class='header-line'></div>");
        html.append("<div class='summary'>— 报告结束 —</div>");
        html.append("</div>");
        
        // JavaScript图表数据
        html.append("<script>");
        html.append(getChartDataScript(reportData));
        html.append("</script>");
        
        // 页脚
        html.append("<div class='footer'>");
        html.append("<div class='page-number'>第 <span class='pageNumber'></span> 页 / 共 <span class='totalPages'></span> 页</div>");
        html.append("</div>");
        
        html.append("</body></html>");
        
        // 使用标准的UTF-8编码写入文件
        Files.write(Paths.get(filePath), html.toString().getBytes(StandardCharsets.UTF_8));
}

private String escapeHtmlContent(String input) {
    if (input == null) return "";
    return input
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&#x27;");
}

private String escapeJavaScriptString(String input) {
    if (input == null) return "";
    return input
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("'", "\\'")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t");
}

private String getStringValue(JsonNode node, String path, String defaultValue) {
    String[] parts = path.split("\\.");
    JsonNode current = node;
    
    for (String part : parts) {
        if (current == null || !current.has(part)) {
            return defaultValue;
        }
        current = current.get(part);
    }
    
    return current != null && !current.isNull() ? current.asText() : defaultValue;
}

private int getIntValue(JsonNode node, String path, int defaultValue) {
    String[] parts = path.split("\\.");
    JsonNode current = node;
    
    for (String part : parts) {
        if (current == null || !current.has(part)) {
            return defaultValue;
        }
        current = current.get(part);
    }
    
    return current != null && !current.isNull() ? current.asInt(defaultValue) : defaultValue;
}

private String getTraitDetailsHtml(JsonNode reportData, String baseKey, String[] traitNames) {
    StringBuilder html = new StringBuilder();
    
    for (String traitName : traitNames) {
        String scorePath = baseKey + "." + traitName + ".score";
        String analysisPath = baseKey + "." + traitName + ".analysis";
        
        int score = getIntValue(reportData, scorePath, 0);
        String analysis = getStringValue(reportData, analysisPath, "—");
        
        html.append("<div class='trait-card'>");
        html.append("<div class='trait-name'>").append(getChineseTraitName(traitName)).append(" (").append(score).append("分)</div>");
        html.append("<div class='trait-details'>");
        html.append("<div class='rating-bar-container'>");
        html.append("<div class='rating-bar-fill' style='width: ").append(score).append("%;'></div>");
        html.append("</div>");
        
        // 检查是否是霍兰德类型，如果是则显示详细分析内容
        if (baseKey.equals("career_traits.holland_types")) {
            String characteristicsPath = baseKey + "." + traitName + ".analysis.characteristics";
            String interestsPath = baseKey + "." + traitName + ".analysis.interests";
            String suitableEnvironmentsPath = baseKey + "." + traitName + ".analysis.suitable_environments";
            String suitableCareersPath = baseKey + "." + traitName + ".analysis.suitable_careers";
            
            String characteristics = getStringValue(reportData, characteristicsPath, "");
            String interests = getStringValue(reportData, interestsPath, "");
            String suitableEnvironments = getStringValue(reportData, suitableEnvironmentsPath, "");
            String suitableCareers = getStringValue(reportData, suitableCareersPath, "");
            
            html.append("<div class='trait-analysis'>");
            if (!characteristics.isEmpty()) {
                html.append("<p><strong>特点：</strong>").append(escapeHtmlContent(characteristics)).append("</p>");
            }
            if (!interests.isEmpty()) {
                html.append("<p><strong>兴趣：</strong>").append(escapeHtmlContent(interests)).append("</p>");
            }
            if (!suitableEnvironments.isEmpty()) {
                html.append("<p><strong>适合环境：</strong>").append(escapeHtmlContent(suitableEnvironments)).append("</p>");
            }
            if (!suitableCareers.isEmpty()) {
                html.append("<p><strong>适合职业：</strong>").append(escapeHtmlContent(suitableCareers)).append("</p>");
            }
            html.append("</div>");
        } else if (baseKey.equals("career_traits.five_powers")) {
            // 对于五力类型，显示心理特质和行为特质
            String psychologicalTraitsPath = baseKey + "." + traitName + ".analysis.psychological_traits";
            String behavioralTraitsPath = baseKey + "." + traitName + ".analysis.behavioral_traits";
            
            String psychologicalTraits = getStringValue(reportData, psychologicalTraitsPath, "");
            String behavioralTraits = getStringValue(reportData, behavioralTraitsPath, "");
            
            html.append("<div class='trait-analysis'>");
            if (!psychologicalTraits.isEmpty()) {
                html.append("<p><strong>心理特质：</strong>").append(escapeHtmlContent(psychologicalTraits)).append("</p>");
            }
            if (!behavioralTraits.isEmpty()) {
                html.append("<p><strong>行为特质：</strong>").append(escapeHtmlContent(behavioralTraits)).append("</p>");
            }
            html.append("</div>");
        } else {
            // 对于其他类型，如八型人格，显示原来的分析
            html.append("<div class='trait-analysis'>").append(escapeHtmlContent(analysis)).append("</div>");
        }
        
        html.append("</div>");
        html.append("</div>");
    }
    
    return html.toString();
}

private String getSubjectDetailsHtml(JsonNode reportData) {
    StringBuilder html = new StringBuilder();
    String[] subjects = {"engineering", "literature", "science", "management", "medicine", "arts", "law", "agriculture", "education", "economics", "history", "philosophy"};
    
    for (String subject : subjects) {
        String scorePath = "major_recommendation.twelve_disciplines." + subject + ".score";
        String analysisPath = "major_recommendation.twelve_disciplines." + subject + ".analysis";
        
        int score = getIntValue(reportData, scorePath, 0);
        String analysis = getStringValue(reportData, analysisPath, "—");
        
        html.append("<div class='subject-card'>");
        html.append("<h4>").append(getChineseSubjectName(subject)).append(" (").append(score).append("分)</h4>");
        html.append("<div class='rating-bar-container'>");
        html.append("<div class='rating-bar-fill' style='width: ").append(score).append("%;'></div>");
        html.append("</div>");
        html.append("<p>").append(escapeHtmlContent(analysis)).append("</p>");
        html.append("</div>");
    }
    
    return html.toString();
}

private String getChartDataScript(JsonNode reportData) {
    StringBuilder script = new StringBuilder();
    
    // 霍兰德六型数据
    String[] hollandTypes = {"realistic", "investigative", "artistic", "social", "enterprising", "conventional"};
    script.append("const hollandData = [");
    for (int i = 0; i < hollandTypes.length; i++) {
        if (i > 0) script.append(",");
        String path = "career_traits.holland_types." + hollandTypes[i] + ".score";
        int score = getIntValue(reportData, path, 0);
        script.append(score);
    }
    script.append("];\n");
    
    // 五力数据
    String[] fivePowers = {"affinity", "driving_force", "execution", "stress_resistance", "planning"};
    script.append("const fivePowersData = [");
    for (int i = 0; i < fivePowers.length; i++) {
        if (i > 0) script.append(",");
        String path = "career_traits.five_powers." + fivePowers[i] + ".score";
        int score = getIntValue(reportData, path, 0);
        script.append(score);
    }
    script.append("];\n");
    
    // 八型人格数据
    String[] eightPersonalityTypes = {"perfectionist_dolphin", "lively_parrot", "brave_tiger", "achiever_lion", 
                                     "feeling_peacock", "thinker_owl", "peaceful_dove", "friendly_panda"};
    script.append("const eightPersonalityData = [");
    for (int i = 0; i < eightPersonalityTypes.length; i++) {
        if (i > 0) script.append(",");
        String path = "career_traits.eight_personality." + eightPersonalityTypes[i] + ".score";
        int score = getIntValue(reportData, path, 0);
        script.append(score);
    }
    script.append("];\n");
    
    // 十二学科数据
    String[] subjects = {"engineering", "literature", "science", "management", "medicine", "arts", "law", "agriculture", "education", "economics", "history", "philosophy"};
    script.append("const twelveSubjectsData = [");
    for (int i = 0; i < subjects.length; i++) {
        if (i > 0) script.append(",");
        String path = "major_recommendation.twelve_disciplines." + subjects[i] + ".score";
        int score = getIntValue(reportData, path, 0);
        script.append(score);
    }
    script.append("];\n");
    
    // 生成图表
    script.append("\n");
    script.append("document.addEventListener('DOMContentLoaded', function() {\n");
    script.append("  drawRadarChart('holland-chart', hollandData, [");
    for (int i = 0; i < hollandTypes.length; i++) {
        if (i > 0) script.append(",");
        script.append("'").append(escapeJavaScriptString(getChineseTraitName(hollandTypes[i]))).append("'");
    }
    script.append("]);\n");
    script.append("  drawRadarChart('five-powers-chart', fivePowersData, [");
    for (int i = 0; i < fivePowers.length; i++) {
        if (i > 0) script.append(",");
        script.append("'").append(escapeJavaScriptString(getChineseTraitName(fivePowers[i]))).append("'");
    }
    script.append("]);\n");
    script.append("  drawRadarChart('eight-personality-chart', eightPersonalityData, [");
    for (int i = 0; i < eightPersonalityTypes.length; i++) {
        if (i > 0) script.append(",");
        script.append("'").append(escapeJavaScriptString(getChineseTraitName(eightPersonalityTypes[i]).split("_")[1])).append("'");
    }
    script.append("]);\n");
    script.append("  drawRadarChart('twelve-subjects-chart', twelveSubjectsData, [");
    for (int i = 0; i < subjects.length; i++) {
        if (i > 0) script.append(",");
        script.append("'").append(escapeJavaScriptString(getChineseSubjectName(subjects[i]))).append("'");
    }
    script.append("]);\n");
    script.append("});\n");
    
    // 图表绘制函数
    script.append("function drawRadarChart(canvasId, data, labels) {\n");
    script.append("  const ctx = document.getElementById(canvasId).getContext('2d');\n");
    script.append("  new Chart(ctx, {\n");
    script.append("    type: 'radar',\n");
    script.append("    data: {\n");
    script.append("      labels: labels,\n");
    script.append("      datasets: [{\n");
    script.append("        data: data,\n");
    script.append("        backgroundColor: 'rgba(0, 82, 217, 0.2)',\n");
    script.append("        borderColor: '#0052D9',\n");
    script.append("        borderWidth: 1,\n");
    script.append("        pointBackgroundColor: '#0052D9',\n");
    script.append("        pointBorderColor: '#fff',\n");
    script.append("        pointHoverBackgroundColor: '#fff',\n");
    script.append("        pointHoverBorderColor: '#0052D9'\n");
    script.append("      }]\n");
    script.append("    },\n");
    script.append("    options: {\n");
    script.append("      scales: {\n");
    script.append("        r: {\n");
    script.append("          beginAtZero: true,\n");
    script.append("          max: 100,\n");
    script.append("          ticks: { stepSize: 20, display: true },\n");
    script.append("          grid: { color: 'rgba(0, 0, 0, 0.1)' },\n");
    script.append("          angleLines: { color: 'rgba(0, 0, 0, 0.1)' },\n");
    script.append("          pointLabels: { font: { size: 12 } }\n");
    script.append("        }\n");
    script.append("      },\n");
    script.append("      plugins: {\n");
    script.append("        legend: { display: false },\n");  // 隐藏图例
    script.append("        tooltip: { enabled: true }\n");
    script.append("      }\n");
    script.append("    }\n");
    script.append("  });\n");
    script.append("}\n");
    
    return script.toString();
}

// 辅助方法：将英文键名转换为中文显示名
private String getChineseTraitName(String englishName) {
    switch (englishName) {
        case "realistic": return "实用型";
        case "investigative": return "研究型";
        case "artistic": return "艺术型";
        case "social": return "社会型";
        case "enterprising": return "企业型";
        case "conventional": return "常规型";
        case "affinity": return "亲和力";
        case "driving_force": return "推动力";
        case "execution": return "执行力";
        case "stress_resistance": return "抗压力";
        case "planning": return "规划力";
        case "perfectionist_dolphin": return "完美型_海豚";
        case "lively_parrot": return "活跃型_鹦鹉";
        case "brave_tiger": return "勇敢型_老虎";
        case "achiever_lion": return "成就型_狮子";
        case "feeling_peacock": return "感觉型_孔雀";
        case "thinker_owl": return "思考型_猫头鹰";
        case "peaceful_dove": return "和平型_和平鸽";
        case "friendly_panda": return "友好型_熊猫";
        default: return englishName;
    }
}

private String getChineseSubjectName(String englishName) {
    switch (englishName) {
        case "engineering": return "工学";
        case "literature": return "文学";
        case "science": return "理学";
        case "management": return "管理学";
        case "medicine": return "医学";
        case "arts": return "艺术学";
        case "law": return "法学";
        case "agriculture": return "农学";
        case "education": return "教育学";
        case "economics": return "经济学";
        case "history": return "历史学";
        case "philosophy": return "哲学";
        default: return englishName;
    }
}
}