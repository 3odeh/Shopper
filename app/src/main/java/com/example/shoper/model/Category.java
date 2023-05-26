package com.example.shoper.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category implements Serializable {
    private String categoryName;
    private String categoryPhoto;
    private static Map<String, String> categoryPhotos = new HashMap<>();
    private final String uri = "https://firebasestorage.googleapis.com/v0/b/shoper-18c8b.appspot.com/o/Category%2FAll.png?alt=media&token=47623e1c-8448-46e6-b9bb-d3ad68a446b1";

    public Category(String categoryName) {
        this.categoryName = categoryName;
        categoryPhoto = uri;
    }

    public Category(String categoryName, String categoryPhoto) {
        this.categoryName = categoryName;
        if (categoryPhoto != null && !categoryPhoto.isEmpty()) {
            this.categoryPhoto = categoryPhoto;
        } else
            this.categoryPhoto = uri;

    }

    public static String getCategoryPhoto(String name){
        String uri ="https://firebasestorage.googleapis.com/v0/b/shoper-18c8b.appspot.com/o/Category%2FAll.png?alt=media&token=47623e1c-8448-46e6-b9bb-d3ad68a446b1";
        return categoryPhotos.getOrDefault(name,uri);
    }

    public static void putCategoryPhoto(String categoryName,String photo){
        categoryPhotos.put(categoryName,photo);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryPhoto() {
        return categoryPhoto;
    }

    public void setCategoryPhoto(String categoryPhoto) {
        this.categoryPhoto = categoryPhoto;
    }


}
