package com.example.florist.Model;

public class Flower{
    String flowerid;
    String flowername;
    int imgid;
    String category;
    float price;
    String color;
    int quantity;

    public Flower(String flowerid, String flowername, String category, float price, String color, int imgid, int quantity) {
        this.flowerid = flowerid;
        this.flowername = flowername;
        this.imgid = imgid;
        this.category = category;
        this.price = price;
        this.color = color;
        this.quantity = quantity;
    }

    public Flower() {
    }

    public String getFlowerid() {
        return flowerid;
    }

    public void setFlowerid(String flowerid) {
        this.flowerid = flowerid;
    }

    public String getFlowername() {
        return flowername;
    }

    public void setFlowername(String flowername) {
        this.flowername = flowername;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}


