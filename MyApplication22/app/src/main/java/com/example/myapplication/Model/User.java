package com.example.myapplication.Model;

import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.myapplication.BR;

public class User extends BaseObservable  {
    private int iduser;
    private String name;
    private String mobile;
    private String email;
    private String password;
    private String address;
    private int idadmin;

    // Getter và Setter cho các thuộc tính

    public User(int iduser,String name, String mobile, String email, String password,String address, int idadmin) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.address=address;
        this.idadmin=idadmin;
    }
    @Bindable
    public int getIduser() {
        if (iduser== 0) {
            return 0;
        }
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
        notifyPropertyChanged(BR.iduser);
    }

@Bindable
    public String getName() {
    if (name== null) {
        return "";
    }
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.email);
    }
@Bindable
    public String getMobile() {
        if (mobile== null) {
            return "";
        }
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }
@Bindable
    public String getEmail() {
    if (email== null) {
        return "";
    }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
notifyPropertyChanged(BR.email);
    }





    @Bindable
    public String getAddress() {
        if (address== null) {
            return "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }



    @Bindable
    public int getIdadmin() {


        return idadmin;
    }

    public void setIdadmin(int idadmin) {
        this.idadmin=idadmin;
        notifyPropertyChanged(BR.idadmin);
    }



@Bindable
    public String getPassword() {
    if (password== null) {
        return "";
    }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }
    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() > 5;
    }
    public boolean isNameLengthGreaterThan5() {
        return getName().length() > 5;
    }
    public boolean isMobileLengthGreaterThan5() {
        return getMobile().length() > 9;
    }
    public boolean isAddressLengthGreaterThan5() {
        return getAddress().length() > 9;
    }
}
