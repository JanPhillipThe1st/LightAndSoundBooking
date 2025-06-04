package com.yamatoapps.lightandsoundbooking;

public class PackageCardItem {
    public  String packageDetails;
    public  int image;
    public Double packagePrice;
    public String name;


    public PackageCardItem(String packageDetails, int image, Double packagePrice, String name) {
        this.packageDetails = packageDetails;
        this.image = image;
        this.packagePrice = packagePrice;
        this.name = name;
    }
}
