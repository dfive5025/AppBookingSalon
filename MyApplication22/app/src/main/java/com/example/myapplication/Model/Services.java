package com.example.myapplication.Model;

import java.io.Serializable;

public class Services implements Serializable {
    String idservice; //sua
    String type;
    String nameservice;
    String price;
    String id_salon;
    int count=0;

    public Services(String idservice, String type, String nameservice, String price, String id_salon) {
        this.idservice = idservice;
        this.type = type;
        this.nameservice = nameservice;
        this.price = price;
        this.id_salon = id_salon;
    }

    public Services() {

    }

    public int getCount() {
        return count;
    }
    public  void addcount(){
        count=count+1;

    }
    public  void removecount(){

if(count!=0)
{
    count=count-1;
}

    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId_salon() {
        return id_salon;
    }

    public void setId_salon(String id_salon) {
        this.id_salon = id_salon;
    }

    boolean isSelected = false;

    public String getIdservice() { //sua
        return idservice;
    }

    public void setIdservice(String idservice) {
        this.idservice = idservice;
    }//sua

    public String getNameservice() {
        return nameservice;
    }

    public void setNameservice(String nameservice) {
        this.nameservice = nameservice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
