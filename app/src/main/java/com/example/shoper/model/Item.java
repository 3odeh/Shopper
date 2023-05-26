package com.example.shoper.model;


import java.util.ArrayList;


public class Item extends Category  {
    private String id;
    private String name;
    private String price;
    private String description;
    private String size;
    private String mainPhoto;
    private ArrayList<String> itemPhoto;

    public Item(Category category,String id){
        super(category.getCategoryName());
        this.id = id;
        price = "price";
        description = "description";
        size = "size";
        name = "name";
        itemPhoto = new ArrayList<>();
        itemPhoto.add(getCategoryPhoto(category.getCategoryName()));
    }


//    public Item(String categoryName, String categoryPhoto, String id, String name, String price, String description, String size, ArrayList<String> itemPhoto) {
//        super(categoryName, categoryPhoto);
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.description = description;
//        this.size = size;
//        this.itemPhoto = itemPhoto;
//    }

    public Item(String categoryName, String id, String size, String name, String price, String description,String mainPhoto) {
        super(categoryName);
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.size = size;
        if(mainPhoto != null && !mainPhoto.isEmpty()){
            this.mainPhoto = mainPhoto;
        }else
            this.mainPhoto = getCategoryPhoto(categoryName);
    }

//    public Item(String categoryName, String id, String size, String name, String price, String description,ArrayList<String> itemPhoto) {
//        super(categoryName);
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.description = description;
//        this.size = size;
//        if(itemPhoto != null && !itemPhoto.isEmpty()){
//            this.itemPhoto = itemPhoto;
//        }else{
//            itemPhoto = new ArrayList<>();
//            itemPhoto.add(getCategoryPhoto());
//        }
//    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public ArrayList<String> getItemPhoto() {
        return itemPhoto;
    }

    public void setItemPhoto(ArrayList<String> itemPhoto) {
        this.itemPhoto = itemPhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
