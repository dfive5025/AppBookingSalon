package com.example.myapplication.Model;

public class Content {

    String namservice;
    String rate;
    String description;
    String usercount;

    public Content(String namservice, String rate, String description, String usercount) {
        this.namservice = namservice;
        this.rate = rate;
        this.description = description;
        this.usercount = usercount;
    }

    public String getUsercount() {
        return usercount;
    }

    public void setUsercount(String usercount) {
        this.usercount = usercount;
    }

    public String getNamservice() {
        return namservice;
    }

    public void setNamservice(String namservice) {
        this.namservice = namservice;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
