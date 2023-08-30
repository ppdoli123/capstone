package com.example.myapplication11;

public class ProductItem {
    private String documentName;
    private String imageUrl;
    private String name;

    private String user;

    private int totalCount; // totalCount 변수 추가

    public ProductItem(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public ProductItem(String name, String imageUrl, String user) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public ProductItem(String name, String imageUrl, String documentName, String user) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.documentName = documentName;
        this.user=user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDocumentName() {
        return documentName;
    }

    public String getUser() {
        return user;
    }
    // getTotalCount 메소드 추가

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
