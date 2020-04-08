package com.bolsoy.finalproject;

import java.util.Date;

public class Item {

    private String title;
    private String description;
    private float price;
    private Date postedTime;

    // No-argument constructor is required to support conversion of Firestore document to POJO
    public Item() {
    }

    // All-argument constructor is required to support conversion of Firestore document to POJO
    public Item(String title, String description, float price, Date postedTime) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.postedTime = postedTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public Date getPostedTime() {
        return postedTime;
    }
}
