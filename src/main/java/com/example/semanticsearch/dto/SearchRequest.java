package com.example.semanticsearch.dto;

public class SearchRequest {
    private String userId;
    private String query;

    public SearchRequest() {
    }

    public SearchRequest(String userId, String query) {
        this.userId = userId;
        this.query = query;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
