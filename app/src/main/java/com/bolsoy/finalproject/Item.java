package com.bolsoy.finalproject;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

class Item {

    private String title;
    private String description;
    private String price;
    private Date createdTime;
    private FirebaseAuth mAuth;
    private String email;
    private String imageFile;

    // No-argument constructor is required to support conversion of Firestore document to POJO
    public Item() {
    }

    // All-argument constructor is required to support conversion of Firestore document to POJO
    public Item(String title, String description, String price, Date createdTime, String email, String imageFile) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.createdTime = createdTime;
        this.email = email;
        this.imageFile = imageFile;
    }

    public String getTitle() {
        return title;
    }

    public String getImageFile() {
        return imageFile;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreatedTime() {
        return createdTime;
    }
}
