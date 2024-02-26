package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("error")
    @Expose
    Boolean error;

    @SerializedName("message")
    @Expose
    String message;


    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
