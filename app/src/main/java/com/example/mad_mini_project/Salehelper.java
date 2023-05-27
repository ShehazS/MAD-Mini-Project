package com.example.mad_mini_project;

public class Salehelper {
    String type,price,quantity,total;

    public Salehelper() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Salehelper(String type, String price, String quantity, String total) {
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }
}
