package com.example.semanticsearch.dto;

public class SearchResponseItem {
    private String text;
    private double score;

    public SearchResponseItem() {
    }

    public SearchResponseItem(String text, double score) {
        this.text = text;
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
