package com.example.florist.Model;

public class Vote {
    private String email;
    private String content;
    private String idflower;
    private float numStar;

    public Vote(String email, String content, String idflower, float numStar) {
        this.email = email;
        this.content = content;
        this.idflower = idflower;
        this.numStar = numStar;
    }
    public Vote() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdflower() {
        return idflower;
    }

    public void setIdflower(String idflower) {
        this.idflower = idflower;
    }

    public float getNumStar() {
        return numStar;
    }

    public void setNumStar(float numStar) {
        this.numStar = numStar;
    }
}