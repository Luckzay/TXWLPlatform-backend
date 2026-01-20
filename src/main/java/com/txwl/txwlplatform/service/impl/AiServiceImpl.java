package com.txwl.txwlplatform.service.impl;

import com.txwl.txwlplatform.model.entity.Question;
import com.txwl.txwlplatform.service.IAiService;
import com.txwl.txwlplatform.util.AiApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AiServiceImpl implements IAiService {

    @Value("${ai.api.key:default-key}")
    private String apiKey;

    @Value("${ai.api.endpoint:https://api.openai.com/v1/chat/completions}")
    private String apiEndpoint;

    @Value("${ai.api.model:gpt-3.5-turbo}")
    private String model;

    private final AiApiClient aiApiClient;

    public AiServiceImpl(AiApiClient aiApiClient) {
        this.aiApiClient = aiApiClient;
    }

    @Override
    public String generateReport(Map<Long, Object> answers) {
        // 构建提示词
        String prompt = buildPrompt(answers);
        
        // 调用AI API
        return aiApiClient.sendRequest(prompt);
    }


    @Override
    public String generateReportWithQuestions(Map<Question, Object> questionAnswerPairs) {
        // 构建包含题目信息的提示词
        String prompt = buildPromptWithQuestions(questionAnswerPairs);
        
        // 调用AI API
        return aiApiClient.sendRequest(prompt);
    }

    private String buildPrompt(Map<Long, Object> answers) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("你是同行未来心理分析师 请分析以下心理测评问卷的结果，并生成一份详细的分析报告。\n\n");
        prompt.append("问卷回答如下：\n");

        for (Map.Entry<Long, Object> entry : answers.entrySet()) {
            prompt.append("问题ID ").append(entry.getKey()).append(": ");
            prompt.append(entry.getValue()).append("\n");
        }

        prompt.append("\n请从以下几个方面进行分析：\n");
        prompt.append("1. 总体性格特征分析\n");
        prompt.append("2. 优势与特长\n");
        prompt.append("3. 待改进的方向\n");
        prompt.append("4. 个人发展建议\n");
        prompt.append("5. 注意事项\n\n");
        prompt.append("请提供专业、有建设性的分析报告，语言要通俗易懂，适合普通用户阅读。");

        return prompt.toString();
    }

    private String buildPromptWithQuestions(Map<Question, Object> questionAnswerPairs) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("请分析以下心理测评问卷的结果，并生成一份详细的分析报告。\n\n");
        prompt.append("问卷内容如下：\n");

        for (Map.Entry<Question, Object> entry : questionAnswerPairs.entrySet()) {
            Question question = entry.getKey();
            Object answer = entry.getValue();
            prompt.append("题目: ").append(question.getQuestionText()).append("\n");
            prompt.append("答案: ").append(answer).append("\n\n");
        }

        prompt.append("\n请从以下几个方面进行分析：\n");
        prompt.append("1. 总体性格特征分析\n");
        prompt.append("2. 优势与特长\n");
        prompt.append("3. 待改进的方向\n");
        prompt.append("4. 个人发展建议\n");
        prompt.append("5. 注意事项\n\n");
        prompt.append("请提供专业、有建设性的分析报告，语言要通俗易懂，适合普通用户阅读。");

        return prompt.toString();
    }
}