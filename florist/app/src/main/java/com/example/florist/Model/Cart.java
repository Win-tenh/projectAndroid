package com.example.florist.Model;
public class Cart {
    public String idflower;
    public String nameflower;
    public int price;
    public int imgflower;
    public int quantity;

    public Cart() {
    }

    public Cart(String idflower, String nameflower, int price, int imgflower, int quantity) {
        this.idflower = idflower;
        this.nameflower = nameflower;
        this.price = price;
        this.imgflower = imgflower;
        this.quantity = quantity;
    }

    public String getIdflower() {
        return idflower;
    }

    public void setIdflower(String idflower) {
        this.idflower = idflower;
    }

    public String getNameflower() {
        return nameflower;
    }

    public void setNameflower(String nameflower) {
        this.nameflower = nameflower;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImgflower() {
        return imgflower;
    }

    public void setImgflower(int imgflower) {
        this.imgflower = imgflower;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

