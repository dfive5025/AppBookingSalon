package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LogEventModel implements Serializable {
    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("name_action")
    @Expose
    String name_action;
    @SerializedName("description")
    @Expose
    String 	description;
    @SerializedName("id_saloon")
    @Expose
    String id_saloon;
    @SerializedName("type_logevent")
    @Expose
    String type_logevent;
    @SerializedName("id_order")
    @Expose
    String id_order;
    @SerializedName("SalonMarkAsRead")
    @Expose
    String SalonMarkAsRead;
    @SerializedName("CustomerMarkAsRead")
    @Expose
    String CustomerMarkAsRead;
    @SerializedName("creator")
    @Expose
    String creator;
    @SerializedName("creatAt")
    @Expose
    String creatAt;

    public LogEventModel(String id, String name_action, String id_saloon, String type_logevent, String id_order, String salonMarkAsRead, String creator, String creatAt) {
        this.id = id;
        this.name_action = name_action;
        this.id_saloon = id_saloon;
        this.type_logevent = type_logevent;
        this.id_order = id_order;
        SalonMarkAsRead = salonMarkAsRead;
        this.creator = creator;
        this.creatAt = creatAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_action() {
        return name_action;
    }

    public void setName_action(String name_action) {
        this.name_action = name_action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId_saloon() {
        return id_saloon;
    }

    public void setId_saloon(String id_saloon) {
        this.id_saloon = id_saloon;
    }

    public String getType_logevent() {
        return type_logevent;
    }

    public void setType_logevent(String type_logevent) {
        this.type_logevent = type_logevent;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getSalonMarkAsRead() {
        return SalonMarkAsRead;
    }

    public void setSalonMarkAsRead(String salonMarkAsRead) {
        SalonMarkAsRead = salonMarkAsRead;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(String creatAt) {
        this.creatAt = creatAt;
    }

    public String getCustomerMarkAsRead() {
        return CustomerMarkAsRead;
    }

    public void setCustomerMarkAsRead(String customerMarkAsRead) {
        CustomerMarkAsRead = customerMarkAsRead;
    }
}
