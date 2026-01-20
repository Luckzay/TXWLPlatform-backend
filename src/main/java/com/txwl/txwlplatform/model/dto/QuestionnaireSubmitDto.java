package com.txwl.txwlplatform.model.dto;

import java.util.Map;

public class QuestionnaireSubmitDto {
    private Long userId;
    private Long paperId;
    private Map<Long, Object> answers;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Map<Long, Object> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, Object> answers) {
        this.answers = answers;
    }
}