package com.example.mad_mini_project;

public class ProductHelperClass {


    String Type,WholesalePrice,RetainPrice,Quantity;

    public ProductHelperClass() {
    }

    public ProductHelperClass(String type, String wholesalePrice, String retainPrice, String quantity) {
        Type = type;
        WholesalePrice = wholesalePrice;
        RetainPrice = retainPrice;
        Quantity = quantity;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getWholesalePrice() {
        return WholesalePrice;
    }

    public void setWholesalePrice(String wholesalePrice) {
        WholesalePrice = wholesalePrice;
    }

    public String getRetainPrice() {
        return RetainPrice;
    }

    public void setRetainPrice(String retainPrice) {
        RetainPrice = retainPrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
