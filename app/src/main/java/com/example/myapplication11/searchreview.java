package com.example.myapplication11;

public class searchreview {
    private String review;
    private String sentence;
    private String name;
    private String positive;
    private String negative;

    private int totalCount; // totalCount 변수 추가


    public searchreview(String name, String review, String positive , String negative) {
        this.name = name;
        this.review = review;
        this.positive = positive;
        this.negative = negative;
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
    public String getPositiveSentence(){
        return positive;
    }
    public String getNegativeSentence(){
        return negative;
    }
    // getTotalCount 메소드 추가

}
