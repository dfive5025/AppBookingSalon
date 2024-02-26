package com.example.myapplication;

import com.example.myapplication.Model.Adminname;
import com.example.myapplication.Model.Apiresponseadd;
import com.example.myapplication.Model.Request;
import com.example.myapplication.Model.Saloon;
import com.example.myapplication.Model.Services;
import com.example.myapplication.Model.Staffcuthair;
import com.example.myapplication.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface backendapi {


    @GET("php/getAllSaloon.php")
    Call<List<Saloon>> getSaloon();


    @POST("php/createuser.php")
    Call<Apiresponseadd> createuser(@Body User user);



    @FormUrlEncoded
    @POST("php/createrequest.php")
    Call<Apiresponseadd> createrequest(
            @Field("name") String name,
            @Field("email") String email,
            @Field("address") String address,
            @Field("mobile") String mobile,
            @Field("order_method") String order_method,
            @Field("note") String note,
            @Field("servicelist") String servicelist,
            @Field("timebegin") String timebegin,
            @Field("timefinish") String timefinish,
            @Field("date") String date,
            @Field("staffcuthairlist") String staffcuthairlist,
            @Field("idsalon") String idsalon,
            @Field("status") String status,
            @Field("payment") String payment,
            @Field("amount") int amount
    );


    @FormUrlEncoded
    @POST("php/userlogin.php")
    Call<Apiresponseadd> userlogin(
            @Field("email") String email,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("php/getstaffbyidsalon.php")
    Call<List<Staffcuthair>> getstaffbyid(
            @Field("idsalon") String idsalon);


    @FormUrlEncoded
    @POST("php/getallservices.php")
    Call<List<Services>> getallservices(

            @Field("idsalon") String idsalon

            );

    @FormUrlEncoded
    @POST("php/getrequestbyid.php")
    Call<List<Request>> getlistsrequestdate(

            @Field("date") String date

    );
    @FormUrlEncoded
    @POST("php/getrequestbyemail.php")
    Call<List<Request>> getlistsrequestbyemail(

            @Field("email") String email

    );

    @FormUrlEncoded
    @POST("php/getSaloonByIdAdminSaloons.php")
    Call<List<Saloon>> getsalonbyid(

            @Field("idadmin") int idadmin

            );

    @FormUrlEncoded
    @POST("php/getSaloonByIdsalon.php")
    Call<List<Saloon>> getsalonbyidsalon(

            @Field("idsalon") int idsalon

    );
    @FormUrlEncoded
    @POST("php/changepassworduser.php")
    Call<Apiresponseadd> changepassworduser(

            @Field("iduser") int iduser,

            @Field("password") String password,@Field("newpassword") String newpassword);
    @GET("php/getAlladmin.php")
    Call<List<Adminname>> getnameadmin(
            );
    @FormUrlEncoded
    @POST("php/getuserbyid.php")
    Call<List<User>> getinforuser(

            @Field("iduser") int iduser);


    @FormUrlEncoded
    @POST("php/updateinfouser.php")
    Call<Apiresponseadd> updateuser(

            @Field("email") String email,
            @Field("iduser") int iduser,
            @Field("name") String name,
            @Field("address") String address,
            @Field("idadmin") int idadmin,
            @Field("mobile") String mobile);



}
