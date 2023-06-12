package com.example.myapplication11;

public class searchitem {
    private String keyword;
    private String imageUrl;
    private String name;
    private String type;
    private int totalCount; // totalCount 변수 추가

    public searchitem(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public searchitem(String name, String imageUrl, String keyword, String type) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.keyword = keyword;
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getKeyword() {
        return keyword;
    }
    // getTotalCount 메소드 추가

}
