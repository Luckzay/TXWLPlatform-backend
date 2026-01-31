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
    public String generateReportWithQuestions(Map<Question, Object> questionAnswerPairs, String userRole) {
        // 构建包含题目信息的提示词
        String prompt = buildPromptWithQuestions(questionAnswerPairs, userRole);
        
        // 调用AI API
        return aiApiClient.sendRequest(prompt);
    }

    private String buildPromptWithQuestions(Map<Question, Object> questionAnswerPairs, String userRole) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("你是心理分析师 请分析以下心理测评问卷的结果，并生成一份详细的分析报告。\n\n");
        prompt.append("你是同行未来职业特质与生涯规划专家。\n" +
                "接下来我会给你一份问卷的作答情况（仅文本），请你严格按照下方“输出格式”要求，用 JSON 返回结果，不要做多余解释，不要输出 markdown 代码块，直接返回纯 JSON。\n" +
                "所有评分 0–100，整数。\n" +
                "所有“分析”字段用中文，每点 20–40 字，禁止出现“根据你的回答”等字样，直接描述特征。\n" +
                "若信息不足，允许合理推断，但禁止暴露推断过程。\n" +
                "所有键名必须与下方完全一致，禁止新增或遗漏。\n" +
                "返回的 JSON 必须合法，可被 Python json.loads 一次性解析。\n" +
                "【输出格式】\n" +
                "{\n" +
                "\"career_traits\": {\n" +
                "\"holland_types\": {\n" +
                "\"realistic\": { \"score\": 0, \"analysis\": { \"characteristics\": \"\", \"interests\": \"\", \"suitable_environments\": \"\", \"suitable_careers\": \"\" } },\n" +
                "\"investigative\": { \"score\": 0, \"analysis\": { \"characteristics\": \"\", \"interests\": \"\", \"suitable_environments\": \"\", \"suitable_careers\": \"\" } },\n" +
                "\"artistic\": { \"score\": 0, \"analysis\": { \"characteristics\": \"\", \"interests\": \"\", \"suitable_environments\": \"\", \"suitable_careers\": \"\" } },\n" +
                "\"social\": { \"score\": 0, \"analysis\": { \"characteristics\": \"\", \"interests\": \"\", \"suitable_environments\": \"\", \"suitable_careers\": \"\" } },\n" +
                "\"enterprising\": { \"score\": 0, \"analysis\": { \"characteristics\": \"\", \"interests\": \"\", \"suitable_environments\": \"\", \"suitable_careers\": \"\" } },\n" +
                "\"conventional\": { \"score\": 0, \"analysis\": { \"characteristics\": \"\", \"interests\": \"\", \"suitable_environments\": \"\", \"suitable_careers\": \"\" } }\n" +
                "},\n" +
                "\"five_powers\": {\n" +
                "\"affinity\": { \"score\": 0, \"analysis\": { \"psychological_traits\": \"\", \"behavioral_traits\": \"\" } },\n" +
                "\"driving_force\": { \"score\": 0, \"analysis\": { \"psychological_traits\": \"\", \"behavioral_traits\": \"\" } },\n" +
                "\"execution\": { \"score\": 0, \"analysis\": { \"psychological_traits\": \"\", \"behavioral_traits\": \"\" } },\n" +
                "\"stress_resistance\": { \"score\": 0, \"analysis\": { \"psychological_traits\": \"\", \"behavioral_traits\": \"\" } },\n" +
                "\"planning\": { \"score\": 0, \"analysis\": { \"psychological_traits\": \"\", \"behavioral_traits\": \"\" } }\n" +
                "},\n" +
                "\"eight_personality\": {\n" +
                "\"perfectionist_dolphin\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"lively_parrot\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"brave_tiger\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"achiever_lion\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"feeling_peacock\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"thinker_owl\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"peaceful_dove\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"friendly_panda\": { \"score\": 0, \"analysis\": \"\" }\n" +
                "},\n" +
                "\"summary\": \"\"\n" +
                "},\n" +
                "\"major_recommendation\": {\n" +
                "\"twelve_disciplines\": {\n" +
                "\"engineering\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"literature\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"science\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"management\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"medicine\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"arts\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"law\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"agriculture\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"education\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"economics\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"history\": { \"score\": 0, \"analysis\": \"\" },\n" +
                "\"philosophy\": { \"score\": 0, \"analysis\": \"\" }\n" +
                "},\n" +
                "\"suitable_disciplines\": \"\"\n" +
                "},\n" +
                "\"career_planning\": {\n" +
                "\"interest_characteristics\": \"\",\n" +
                "\"ability_temperament\": \"\",\n" +
                "\"personality_thinking\": \"\",\n" +
                "\"talent_subjects\": \"\"\n" +
                "}\n" +
                "}\n" +
                "【用户输入】\n" +
                "请等待，我将把问卷作答情况粘贴在下面。\n");
        prompt.append("用户身份为：" + userRole);

        for (Map.Entry<Question, Object> entry : questionAnswerPairs.entrySet()) {
            Question question = entry.getKey();
            Object answer = entry.getValue();
            prompt.append("题目: ").append(question.getQuestionText()).append("\n");
            prompt.append("答案: ").append(answer).append("\n\n");
        }

        return prompt.toString();
    }
}