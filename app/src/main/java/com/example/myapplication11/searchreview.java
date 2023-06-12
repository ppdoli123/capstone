package com.example.myapplication11;

public class searchreview {
    private String review;
    private String sentence;
    private String name;

    private int totalCount; // totalCount 변수 추가


    public searchreview(String name, String review, String sentence) {
        this.name = name;
        this.review = review;
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }

    public String getName() {
        return name;
    }

    public String getReview() {
        return review;
    }
    // getTotalCount 메소드 추가

}
