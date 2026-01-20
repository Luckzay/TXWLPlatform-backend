package com.txwl.txwlplatform.service.impl;

import com.txwl.txwlplatform.mapper.ReportMapper;
import com.txwl.txwlplatform.model.entity.Question;
import com.txwl.txwlplatform.model.entity.Report;
import com.txwl.txwlplatform.model.entity.User;
import com.txwl.txwlplatform.service.IAiService;
import com.txwl.txwlplatform.service.IQuestionService;
import com.txwl.txwlplatform.service.IQuestionnaireSubmissionService;
import com.txwl.txwlplatform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public String submitQuestionnaire(Long userId, Long paperId, Map<Long, Object> answers) {
        // 根据传入的userId（可能是手机号）查找真实的用户ID
        User user = userService.getUserById(userId);
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
        String aiGeneratedReport = aiService.generateReportWithQuestions(questionAnswerPairs);
        System.out.println("AI生成的报告: " + aiGeneratedReport);

        // 生成报告文件（HTML格式）
        String reportUrl = generateHtmlReportFile(aiGeneratedReport, actualUserId, paperId);

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

    private String generateHtmlReportFile(String reportContent, Long userId, Long paperId) {
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
            java.io.File directory = new java.io.File(basePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // 生成HTML报告
            generateHtmlReport(fullPath, reportContent, userId, paperId);
            
            // 返回相对于应用的路径
            return "/reports/" + fileName;
        } catch (Exception e) {
            System.err.println("生成HTML报告文件失败: " + e.getMessage());
            e.printStackTrace();
            // 返回错误路径或抛出异常
            throw new RuntimeException("生成HTML报告文件失败", e);
        }
    }
    
    private void generateHtmlReport(String filePath, String reportContent, Long userId, Long paperId) throws Exception {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"zh-CN\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>心理测评报告</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: 'Microsoft YaHei', Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }\n");
        html.append("        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 20px rgba(0,0,0,0.1); }\n");
        html.append("        h1 { color: #2c3e50; text-align: center; border-bottom: 2px solid #3498db; padding-bottom: 10px; }\n");
        html.append("        h2 { color: #34495e; margin-top: 25px; }\n");
        html.append("        h3 { color: #34495e; margin-top: 20px; }\n");
        html.append("        h4 { color: #34495e; margin-top: 15px; }\n");
        html.append("        h5 { color: #34495e; margin-top: 10px; }\n");
        html.append("        h6 { color: #34495e; margin-top: 8px; }\n");
        html.append("        p { line-height: 1.6; color: #34495e; }\n");
        html.append("        .info-box { background-color: #ecf0f1; padding: 15px; border-radius: 5px; margin: 20px 0; }\n");
        html.append("        .section { margin: 20px 0; padding: 15px; border-left: 4px solid #3498db; background-color: #f8f9fa; }\n");
        html.append("        ul { margin: 10px 0; padding-left: 20px; }\n");
        html.append("        ol { margin: 10px 0; padding-left: 20px; }\n");
        html.append("        li { margin: 5px 0; }\n");
        html.append("        .blockquote { margin: 10px 0; padding: 10px 15px; border-left: 3px solid #ccc; background-color: #f9f9f9; font-style: italic; }\n");
        html.append("        strong { font-weight: bold; }\n");
        html.append("        em { font-style: italic; }\n");
        html.append("        .footer { margin-top: 30px; text-align: center; color: #7f8c8d; font-size: 0.9em; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <h1>心理测评分析报告</h1>\n");
        html.append("        <div class=\"info-box\">\n");
        html.append("            <p><strong>用户ID:</strong> ").append(userId).append("</p>\n");
        html.append("            <p><strong>试卷ID:</strong> ").append(paperId).append("</p>\n");
        html.append("            <p><strong>生成时间:</strong> ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("</p>\n");
        html.append("        </div>\n");
        html.append("        <div class=\"section\">\n");
        html.append("            ").append(convertMarkdownToHtml(reportContent)).append("\n");
        html.append("        </div>\n");
        html.append("        <div class=\"footer\">\n");
        html.append("            <p>© 2026 TXWL Platform - 专业心理测评系统</p>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>");
        
        // 写入文件
        java.nio.file.Files.write(java.nio.file.Paths.get(filePath), html.toString().getBytes("UTF-8"));
    }
    
    private String convertMarkdownToHtml(String markdown) {
        String html = markdown;
        
        // 处理标题
        html = html.replaceAll("(?m)^###### (.*?)$", "<h6>$1</h6>");
        html = html.replaceAll("(?m)^##### (.*?)$", "<h5>$1</h5>");
        html = html.replaceAll("(?m)^#### (.*?)$", "<h4>$1</h4>");
        html = html.replaceAll("(?m)^### (.*?)$", "<h3>$1</h3>");
        html = html.replaceAll("(?m)^## (.*?)$", "<h2>$1</h2>");
        html = html.replaceAll("(?m)^# (.*?)$", "<h1>$1</h1>");
        
        // 处理粗体和斜体
        html = html.replaceAll("\\*\\*(.*?)\\*\\*", "<strong>$1</strong>");
        html = html.replaceAll("__(.*?)__", "<strong>$1</strong>");
        html = html.replaceAll("(^|\\s)\\*(.*?)\\*($|\\s)", "$1<em>$2</em>$3");
        html = html.replaceAll("(^|\\s)_(.*?)_($|\\s)", "$1<em>$2</em>$3");
        
        // 处理删除线
        html = html.replaceAll("~~(.*?)~~", "<del>$1</del>");
        
        // 处理链接
        html = html.replaceAll("\\[(.*?)\\]\\((.*?)\\)", "<a href=\"$2\">$1</a>");
        
        // 处理内联代码
        html = html.replaceAll("`([^`]+)`", "<code>$1</code>");
        
        // 处理无序列表
        html = html.replaceAll("(?m)^\\s*- (.*?)(?=\\n|$)", "<li>$1</li>");
        html = html.replaceAll("<li>(.*?)</li>(?=\n<li>)", "<li>$1</li>\n");
        html = html.replaceAll("(?m)(^|\n)(<li>.*?</li>\n?)+", "<ul>\n$0</ul>\n");
        
        // 更精确地处理列表
        String[] lines = html.split("\n");
        StringBuilder result = new StringBuilder();
        boolean insideUnorderedList = false;
        boolean insideOrderedList = false;
        
        for (String line : lines) {
            line = line.trim();
            if (line.matches("^-\\s.*")) {
                // 无序列表项
                if (!insideUnorderedList) {
                    if (insideOrderedList) {
                        result.append("</ol>\n");
                        insideOrderedList = false;
                    }
                    result.append("<ul>\n");
                    insideUnorderedList = true;
                }
                String content = line.substring(2).trim();
                result.append("<li>").append(content).append("</li>\n");
            } else if (line.matches("^\\d+\\.\\s.*")) {
                // 有序列表项
                if (!insideOrderedList) {
                    if (insideUnorderedList) {
                        result.append("</ul>\n");
                        insideUnorderedList = false;
                    }
                    result.append("<ol>\n");
                    insideOrderedList = true;
                }
                String content = line.replaceFirst("^\\d+\\.\\s", "");
                result.append("<li>").append(content).append("</li>\n");
            } else {
                // 普通段落或其他内容
                if (insideUnorderedList) {
                    result.append("</ul>\n");
                    insideUnorderedList = false;
                }
                if (insideOrderedList) {
                    result.append("</ol>\n");
                    insideOrderedList = false;
                }
                
                if (!line.isEmpty() && !line.startsWith("<h") && !line.startsWith("<p>") && 
                    !line.startsWith("<ul") && !line.startsWith("<ol") && !line.startsWith("<li") && 
                    !line.startsWith("<div") && !line.startsWith("<span") && !line.startsWith("<a") && 
                    !line.startsWith("<img") && !line.startsWith("<table") && !line.startsWith("<tr") && 
                    !line.startsWith("<td") && !line.startsWith("<th") && !line.startsWith("<blockquote")) {
                    result.append("<p>").append(line).append("</p>\n");
                } else {
                    result.append(line).append("\n");
                }
            }
        }
        
        // 结束任何未关闭的列表
        if (insideUnorderedList) {
            result.append("</ul>\n");
        }
        if (insideOrderedList) {
            result.append("</ol>\n");
        }
        
        html = result.toString();
        
        // 处理引用块
        html = html.replaceAll("(?m)^>\\s(.*)$", "<div class=\"blockquote\">$1</div>");
        
        // 替换换行符为段落标签（对于独立的行）
        html = html.replaceAll("\n\n", "</p><p>");
        html = html.replaceAll("\n", "<br/>");
        html = html.replaceAll("<p><br/>", "<p>");
        html = html.replaceAll("<br/></p>", "</p>");
        html = html.replaceAll("<p></p>", "");
        
        return html;
    }
}