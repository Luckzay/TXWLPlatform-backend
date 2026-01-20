package com.txwl.txwlplatform.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class AiApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ai.api.key:sk-your-api-key-here}")
    private String apiKey;

    @Value("${ai.api.endpoint:https://api.openai.com/v1/chat/completions}")
    private String apiEndpoint;

    @Value("${ai.api.model:gpt-3.5-turbo}")
    private String model;

    public AiApiClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String sendRequest(String prompt) {
        try {
            // 构建请求体 - 阿里云格式
            String requestBody = buildAliyunRequestBody(prompt);

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);
            headers.set("User-Agent", "TXWLPlatform/1.0");

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // 发送请求
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                apiEndpoint,
                requestEntity,
                String.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return parseAliyunResponse(responseEntity.getBody());
            } else {
                throw new RuntimeException("API请求失败: " + responseEntity.getStatusCode() + " - " + responseEntity.getBody());
            }
        } catch (Exception e) {
            System.err.println("AI API调用失败: " + e.getMessage());
            e.printStackTrace();
            return generateFallbackResponse();
        }
    }

    private String buildAliyunRequestBody(String prompt) {
        // 阿里云通义千问API兼容OpenAI格式的请求格式
        return String.format(
            "{\n" +
            "  \"model\": \"%s\",\n" +
            "  \"messages\": [\n" +
            "    {\"role\": \"system\", \"content\": \"你是专业的心理学分析师，擅长根据问卷结果生成详细的心理分析报告。\"},\n" +
            "    {\"role\": \"user\", \"content\": \"%s\"}\n" +
            "  ]\n" +
            "}", 
            model,
            escapeJson(prompt)
        );
    }

    private String escapeJson(String input) {
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    private String parseAliyunResponse(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            
            // 阿里云API返回格式可能略有不同
            JsonNode outputNode = rootNode.get("output");
            if (outputNode != null) {
                JsonNode textNode = outputNode.get("text");
                if (textNode != null) {
                    return textNode.asText();
                }
            }
            
            // 如果标准路径不存在，尝试其他可能的路径
            JsonNode choicesNode = rootNode.get("choices");
            if (choicesNode != null && choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode firstChoice = choicesNode.get(0);
                JsonNode messageNode = firstChoice.get("message");
                
                if (messageNode != null) {
                    return messageNode.get("content").asText();
                }
            }
            
            System.err.println("无法解析AI响应: " + responseBody);
            return "无法解析AI返回结果: " + responseBody;
        } catch (Exception e) {
            System.err.println("解析AI响应失败: " + e.getMessage());
            e.printStackTrace();
            return "解析AI响应失败: " + e.getMessage();
        }
    }

    private String generateFallbackResponse() {
        return "AI分析服务暂时不可用，请稍后重试。";
    }
}