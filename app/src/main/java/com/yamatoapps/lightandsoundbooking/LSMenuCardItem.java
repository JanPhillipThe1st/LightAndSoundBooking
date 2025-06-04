package com.yamatoapps.lightandsoundbooking;

public class LSMenuCardItem {
    public  String name;
    public  Double rating;
    public  String description;
    public String image_url;
    public String id = "";


    public LSMenuCardItem(String name, Double rating, String description, String image_url, String id) {
        this.name = name;
        this.rating = rating;
        this.description = description;
        this.image_url = image_url;
        this.id = id;
    }
}
