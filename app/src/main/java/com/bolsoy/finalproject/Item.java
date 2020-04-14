package com.bolsoy.finalproject;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class Item {

    private String title;
    private String description;
    private String price;
    private Date postedTime;
    private FirebaseAuth mAuth;
    private String email;

    // No-argument constructor is required to support conversion of Firestore document to POJO
    public Item() {
    }

    // All-argument constructor is required to support conversion of Firestore document to POJO
    public Item(String title, String description, String price, Date postedTime, String email) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.postedTime = postedTime;
        this.email = email;
    }

    public String getTitle() {
        return title;
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
        return postedTime;
    }
}
