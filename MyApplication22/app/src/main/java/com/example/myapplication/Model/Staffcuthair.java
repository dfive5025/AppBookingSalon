package com.example.myapplication.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Staffcuthair implements Serializable {
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("name")
    String name;
    @SerializedName("mobile")
    String mobile;
    @SerializedName("address")
    String address;
    @SerializedName("experience")
    String experience;
    @SerializedName("work")
    String work;

    String Birthday;
    @SerializedName("salary")
    int salary;
    @SerializedName("avgrate")
    String avgrate;
    @SerializedName("idsaloon")
    String idsaloon;
    @SerializedName("idstaff")
    String idstaff;
    @SerializedName("image")
    String image;

    public Staffcuthair() {
    }

    public Staffcuthair(String name, String email, String password, String mobile, String address, String experience, String work, int salary, String avgrate, String idsaloon, String image, String idstaff) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.experience = experience;
        this.work = work;
        this.salary = salary;
        this.avgrate = avgrate;
        this.idsaloon = idsaloon;
        this.email = email;
        this.password = password;
        this.image = image;
        this.idstaff = idstaff;
    }

    public Staffcuthair(String name, String email, String password, String Birthday, String mobile, String address, String experience, String work, int salary, String avgrate, String idsaloon, String image, String idstaff) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.Birthday = Birthday;
        this.experience = experience;
        this.work = work;
        this.salary = salary;
        this.avgrate = avgrate;
        this.idsaloon = idsaloon;
        this.email = email;
        this.password = password;
        this.image = image;
        this.idstaff = idstaff;
    }

    public String getIdstaff() {
        return idstaff;
    }

    public void setIdstaff(String idstaff) {
        this.idstaff = idstaff;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getIdsaloon() {
        return idsaloon;
    }

    public void setIdsaloon(String idsaloon) {
        this.idsaloon = idsaloon;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getExperience() {
        return experience;
    }

    public String getWork() {
        return work;
    }

    public int getSalary() {
        return salary;
    }

    public String getAvgrate() {
        return avgrate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setAvgrate(String avgrate) {
        this.avgrate = avgrate;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }


}
