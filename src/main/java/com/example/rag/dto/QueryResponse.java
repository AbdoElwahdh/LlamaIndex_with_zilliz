package com.example.rag.dto;

public class QueryResponse {
    private String answer;
    private float score;

    public QueryResponse(String answer, float score) {
        this.answer = answer;
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public float getScore() {
        return score;
    }
}
