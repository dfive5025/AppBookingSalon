package com.example.myapplication.Model;

import java.io.Serializable;

public class Saloon implements Serializable {
    String id_saloon;
    String name;
    String address;
    String mobileNo;
    String workingHr;
    String area;
    String email;
    String password;
    String image;
    String ext;
    String id_AdminSaloons;
    Double avgRating;

    public Saloon(String name, String address, String mobileNo, String workingHr, String area, String email, String password, String image, String ext, String id_saloon, Double avgRating){
        this.id_saloon=id_saloon;
        this.name = name;
        this.address = address;
        this.area = area;
        this.mobileNo = mobileNo;
        this.workingHr = workingHr;
        this.email = email;
        this.password = password;
        this.image = image;
        this.ext = ext;
        this.avgRating=avgRating;
    }

    public Saloon(String id_saloon, String name, String address, String mobileNo, String workingHr, String area, String email, String password, String image, String id_AdminSaloons) {
        this.id_saloon = id_saloon;
        this.name = name;
        this.workingHr=workingHr;
        this.address = address;
        this.mobileNo = mobileNo;
        this.area = area;
        this.email = email;
        this.password = password;
        this.image = image;
        this.id_AdminSaloons = id_AdminSaloons; //sua141

    }

    public String getId_saloon() {
        return id_saloon;
    }

    public void setId_saloon(String id_saloon) {
        this.id_saloon = id_saloon;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public String getExt() {
        return ext;
    }

    public void setId(String id) {
        this.id_AdminSaloons = id;
    }

    public String getId() {
        return id_AdminSaloons;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public String getWorkingHr() {
        return workingHr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getId_AdminSaloons() {
        return id_AdminSaloons;
    }

    public void setId_AdminSaloons(String id_AdminSaloons) {
        this.id_AdminSaloons = id_AdminSaloons;
    }

    public void setWorkingHr(String workingHr) {
        this.workingHr = workingHr;
    }
//    public Map<String,Object>toMap(){
//        HashMap<String,Object>result=new HashMap<>();
//        result.put("name",name);
//        return  result;
//    }
}
