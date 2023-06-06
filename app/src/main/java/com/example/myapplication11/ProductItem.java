package com.example.myapplication11;

public class ProductItem {
    private String documentName;
    private String imageUrl;
    private String name;
    private int totalCount; // totalCount 변수 추가

    public ProductItem(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public ProductItem(String name, String imageUrl, String documentName) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.documentName = documentName;
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
    // getTotalCount 메소드 추가

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
