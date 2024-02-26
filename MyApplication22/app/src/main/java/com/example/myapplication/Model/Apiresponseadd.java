package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

public class Apiresponseadd {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private UserData data;
    @SerializedName("token")
    private String token;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
    public UserData getData() {
        return data;
    }
    public class UserData {
        @SerializedName("id_user")
        private int userId;

        @SerializedName("token")
        private String token;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
