package com.example.florist.Model;

public class Order {
    String idorder,email,location,namecus;
    String nameflower,idflower;
    int img,quantity,price,phone;

    public Order() {
    }

    public Order(String idorder, String email, String location, String namecus, String nameflower, String idflower, int img, int quantity, int price, int phone) {
        this.idorder = idorder;
        this.email = email;
        this.location = location;
        this.namecus = namecus;
        this.nameflower = nameflower;
        this.idflower = idflower;
        this.img = img;
        this.quantity = quantity;
        this.price = price;
        this.phone = phone;
    }

    public String getIdorder() {
        return idorder;
    }

    public void setIdorder(String idorder) {
        this.idorder = idorder;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNamecus() {
        return namecus;
    }

    public void setNamecus(String namecus) {
        this.namecus = namecus;
    }

    public String getNameflower() {
        return nameflower;
    }

    public void setNameflower(String nameflower) {
        this.nameflower = nameflower;
    }

    public String getIdflower() {
        return idflower;
    }

    public void setIdflower(String idflower) {
        this.idflower = idflower;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
