package com.example.myapplication11;

public class communityitem {
    private String title;
    private String text;
    private int totalCount; // totalCount 변수 추가

    public communityitem(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    // getTotalCount 메소드 추가

}
