package com.example.mad_mini_project;

public class ItemsHelperDb {

    public String Quantity;
    public String RPrice;

    public ItemsHelperDb() {
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRPrice() {
        return RPrice;
    }

    public void setRPrice(String RPrice) {
        this.RPrice = RPrice;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getWPrice() {
        return WPrice;
    }

    public void setWPrice(String WPrice) {
        this.WPrice = WPrice;
    }

    public String Type;
    public String WPrice;

    public ItemsHelperDb(String quantity, String RPrice, String type, String WPrice) {
        Quantity = quantity;
        this.RPrice = RPrice;
        Type = type;
        this.WPrice = WPrice;
    }
    @Override
    public String toString(){
        return "Type: "+Type+"\nQuantity: "+Quantity+"\nRetail-Price: "+RPrice+"\nWholesale-Price: "+WPrice;
    }
}
