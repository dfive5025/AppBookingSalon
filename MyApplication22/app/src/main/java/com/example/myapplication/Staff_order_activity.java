
package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Model.Apiresponseadd;
import com.example.myapplication.Model.Request;
import com.example.myapplication.Model.Services;
import com.example.myapplication.Model.Staffcuthair;
import com.example.myapplication.adapter.Staff_order_adapter;
import com.example.myapplication.adapter.Staff_order_adapter_show;
import com.example.myapplication.adapter.staffchooseorder;
import com.example.myapplication.constants.constants;

import com.example.myapplication.repository.RetrofitInstance;
import com.example.myapplication.repository.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.dialogplus.DialogPlus;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Staff_order_activity extends AppCompatActivity {
    ArrayList<Staffcuthair> staffchoose1;
    Staff_order_adapter_show adapterbabe;
    String name_staff, email_staff, Workinghr;
    ArrayList<Services> selectServicesList;
    String mobile_staff;
    String address_staff;

    String status, Saloonid, Adminid;
    String howorder, howpayment;
    String date, time_begin, time_finish;
    Button btn_submit, btn_select, btn_shedule;
    RecyclerView rec2;
    ImageView btn_back;

    constants constants = new constants();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(constants.baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    com.example.myapplication.backendapi backendapi = retrofit.create(com.example.myapplication.backendapi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_order_staff);

        getinit();
        getintentdata(getIntent());
        setevent();


    }
//    public void onReceive() {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
//        String currentTime = dateFormat.format(calendar.getTime());
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//        Notification builder = new NotificationCompat.Builder(this, notification1.notiId)
//                .setSmallIcon(R.drawable.ic_notification)
//                .setLargeIcon(bitmap)
//                .setContentTitle("Đặt Lịch Thành Công, Khách Hàng "+mobile_staff)
//                .setContentText("Tạo Lịch Hẹn Mới Thành Công Lúc "+currentTime)
//                .build();
//
//        // Hiển thị thông báo
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Toast.makeText(Staff_order_activity.this, String.valueOf(getNotificationId()),Toast.LENGTH_SHORT).show();
//        notificationManager.notify(getNotificationId(), builder);
//    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }

    private static final String CHANNEL_ID = "your_channel_id"; // ID của kênh thông báo
    private static final int NOTIFICATION_ID = 1; // ID của thông báo

    @SuppressLint("MissingPermission")
    public static void showOrderSuccessNotification(Context context, String date, String time_begin, String time_finish) {
        // Kiểm tra phiên bản Android và tạo kênh thông báo nếu cần
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Your Channel Name";
            String description = "Your Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Xây dựng thông báo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification) // Icon cho thông báo
                .setContentTitle("Đặt hàng thành công") // Tiêu đề của thông báo
                .setContentText("Đơn hàng của bạn đã được đặt thành công: " + "Ngày :" + date + " " + time_begin + "->" + time_finish) // Nội dung thông báo
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Hiển thị thông báo
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void setevent() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBackPressed();
            }
        });

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getlist();
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (staffchoose1.size() == 0) {
                    Toast.makeText(Staff_order_activity.this, "Chưa chọn nhân viên", Toast.LENGTH_SHORT).show();
                    return;
                }


//                Call<List<Request>> call1=backendapi.getlistsrequestdate(date);
//                call1.enqueue(new Callback<List<Request>>() {
//                    @Override
//                    public void onResponse(Call<List<Request>> call, retrofit2.Response<List<Request>> response) {
//
//                        List<Request> requestList=new ArrayList<>();
//                        requestList.addAll(response.body());
//                        for (Request request1:requestList)
//                        {
//
//                            String dateso = request1.getDate();
//                            String time_beginso = request1.getTime_begin();
//                            String time_finishso = request1.getTime_finish();
//                            String[] dateso1 = dateso.trim().split("-");
//                            String[] dateso2 = date.trim().split("-");
//                            String[] timebeginso1 = time_beginso.trim().split(":");
//                            String[] timebeginso2 = time_begin.trim().split(":");
//                            String[] timefinishso1 = time_finishso.trim().split(":");
//                            String[] timefinishso2 = time_finish.trim().split(":");
//
//
//                            if (Integer.parseInt(dateso1[0]) == Integer.parseInt(dateso2[0]) && Integer.parseInt(dateso1[1]) == Integer.parseInt(dateso2[1]) && Integer.parseInt(dateso1[2]) == Integer.parseInt(dateso2[2])) {
//                                if (Integer.parseInt(timebeginso1[0]) > Integer.parseInt(timefinishso2[0])) {
//                                } else if (Integer.parseInt(timefinishso1[0]) < Integer.parseInt(timebeginso2[0])) {
//                                } else if (Integer.parseInt(timefinishso1[0]) == Integer.parseInt(timebeginso2[0]) && Integer.parseInt(timefinishso1[1]) <= Integer.parseInt(timebeginso2[1])) {
//                                } else if (Integer.parseInt(timebeginso1[0]) == Integer.parseInt(timefinishso2[0]) && Integer.parseInt(timebeginso1[1]) >= Integer.parseInt(timefinishso2[1])) {
//                                } else {
//
//                                    for(Staffcuthair staffcuthair:request1.getStaffcuthairList())
//                                    { Boolean isreal = false;
//                                        int index = -10;
//                                        for(Staffcuthair element : staffchoose1){
//                                            if(element.getEmail().equals(staffcuthair.getEmail()))
//                                            {
//                                                isreal = true;
//                                                index = staffchoose1.indexOf(element);
//                                            }
//                                        }
//                                        if (isreal) {
//                                            Toast.makeText(Staff_order_activity.this, "Nhân viên được đăng ký đã bận", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }
//
//                                    }
//
//
//
//
//                                }
//
//
//                            }
//
//
//
//
//
//                        }
//
//                        Request request = new Request(name_staff, mobile_staff, email_staff, address_staff, "", howorder, selectServicesList, date, time_begin, time_finish, staffchoose1, "", Saloonid, "Pending", howpayment, Integer.parseInt(totalmoney));
//
//                        Call<Apiresponseadd> call2= backendapi.createrequest(request);
//                        call2.enqueue(new Callback<Apiresponseadd>() {
//                            @Override
//                            public void onResponse(Call<Apiresponseadd> call, retrofit2.Response<Apiresponseadd> response) {
//
//
//                                if (response)
//                            }
//
//                            @Override
//                            public void onFailure(Call<Apiresponseadd> call, Throwable t) {
//
//                            }
//                        });
//
//
//
//
//
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Request>> call, Throwable t) {
//                        Toast.makeText(Staff_order_activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d("faie",t.getMessage());
//                    }
//                });


                Call<List<Request>> call1 = backendapi.getlistsrequestdate(date);
                call1.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, retrofit2.Response<List<Request>> response) {

                        List<Request> requestList = new ArrayList<>();
                        requestList.addAll(response.body());
                        for (Request request1 : requestList) {

                            String dateso = request1.getDate();
                            String time_beginso = request1.getTime_begin();
                            String time_finishso = request1.getTime_finish();
                            String[] dateso1 = dateso.trim().split("-");
                            String[] dateso2 = date.trim().split("-");
                            String[] timebeginso1 = time_beginso.trim().split(":");
                            String[] timebeginso2 = time_begin.trim().split(":");
                            String[] timefinishso1 = time_finishso.trim().split(":");
                            String[] timefinishso2 = time_finish.trim().split(":");
                            String staffcuthairList1 = request1.getJsonlist();

                            Gson gson2 = new Gson();
                            Type type1 = new TypeToken<ArrayList<Staffcuthair>>() {
                            }.getType();
                            ArrayList<Staffcuthair> staffList1 = gson2.fromJson(staffcuthairList1, type1);
                            request1.setStaffcuthairList(staffList1);

                            if (Integer.parseInt(dateso1[0]) == Integer.parseInt(dateso2[0]) && Integer.parseInt(dateso1[1]) == Integer.parseInt(dateso2[1]) && Integer.parseInt(dateso1[2]) == Integer.parseInt(dateso2[2])) {
                                if (Integer.parseInt(timebeginso1[0]) > Integer.parseInt(timefinishso2[0])) {
                                } else if (Integer.parseInt(timefinishso1[0]) < Integer.parseInt(timebeginso2[0])) {
                                } else if (Integer.parseInt(timefinishso1[0]) == Integer.parseInt(timebeginso2[0]) && Integer.parseInt(timefinishso1[1]) <= Integer.parseInt(timebeginso2[1])) {
                                } else if (Integer.parseInt(timebeginso1[0]) == Integer.parseInt(timefinishso2[0]) && Integer.parseInt(timebeginso1[1]) >= Integer.parseInt(timefinishso2[1])) {
                                } else {

                                    for (Staffcuthair staffcuthair : request1.getStaffcuthairList()) {
                                        Boolean isreal = false;
                                        int index = -10;
                                        for (Staffcuthair element : staffchoose1) {
                                            if (element.getEmail().equals(staffcuthair.getEmail())) {
                                                isreal = true;
                                                index = staffchoose1.indexOf(element);
                                            }
                                        }
                                        if (isreal) {
                                            Toast.makeText(Staff_order_activity.this, "Nhân viên được đăng ký đã bận", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    }


                                }


                            }


                        }


                        Request request = new Request(name_staff, mobile_staff, email_staff, address_staff, "", howorder, selectServicesList, date, time_begin, time_finish, staffchoose1, "", Saloonid, "Pending", howpayment, Integer.parseInt(totalmoney));

                        Gson gson = new Gson();
                        String jsonservice = gson.toJson(request.getServices());
                        Gson gson1 = new Gson();
                        String jsonstaff = gson1.toJson(request.getStaffcuthairList());
                        Map<String, String> params = new HashMap<>();

                        RetrofitInterface retrofitInterface = RetrofitInstance.getService();
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        String token = sharedPref.getString("Token", "");
                        int IDuser = sharedPref.getInt("iduser", 0);
                        Log.d("token1", token);
                        RetrofitInstance.setToken(token);
                        Call<Apiresponseadd> call2 = retrofitInterface.createrequest(IDuser, request.getName(), request.getEmail(), request.getAddres(), request.getMobile(), request.getOrder(), request.getNote(), jsonservice, request.getTime_begin(), request.getTime_finish(), request.getDate(), jsonstaff, request.getId_saloon(), request.getStatus(), request.getPayment(), request.getAmount());
                        call2.enqueue(new Callback<Apiresponseadd>() {
                            @Override
                            public void onResponse(Call<Apiresponseadd> call, retrofit2.Response<Apiresponseadd> response) {
                                Toast.makeText(Staff_order_activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                if (response.body().getMessage().equals("successfully")) {
                                    showOrderSuccessNotification(getApplicationContext(), date, time_begin, time_finish);
                                    Toast.makeText(Staff_order_activity.this, "Đặt thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Staff_order_activity.this, Successmonitoractivity.class);
                                    intent.putExtra("name_staff", name_staff);
                                    intent.putExtra("email_staff", email_staff);
                                    intent.putExtra("mobile_staff", mobile_staff);
                                    intent.putExtra("address_staff", address_staff);

                                    intent.putExtra("howorder", howorder);
                                    intent.putExtra("howpayment", howpayment);
                                    //       intent.putExtra("selectServicesList",selectServicesList);
                                    intent.putExtra("status", status);
                                    intent.putExtra("Saloonid", Saloonid);
                                    intent.putExtra("Adminid", Adminid);
                                    intent.putExtra("Workinghr", Workinghr);
                                    intent.putExtra("date", date);

                                    intent.putExtra("time_begin", time_begin);
                                    intent.putExtra("time_finish", time_finish);
                                    intent.putExtra("selectServicesList", (Serializable) selectServicesList);
                                    intent.putExtra("totalmoney", totalmoney);
                                    startActivity(intent);

                                }

                            }

                            @Override
                            public void onFailure(Call<Apiresponseadd> call, Throwable t) {
                                Toast.makeText(Staff_order_activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {
                        Toast.makeText(Staff_order_activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("faie", t.getMessage());
                    }
                });


//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
//                        constants.URL_getrequestbydate,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    if(!jsonObject.getBoolean("error")) {
////                                        Toast.makeText(Staff_order_activity.this,"thanhcong", Toast.LENGTH_SHORT).show();
//                                        //    String request=jsonObject.getString("request");
//                                        // Toast.makeText(Staff_order_activity.this, request, Toast.LENGTH_SHORT).show();
//                                        JSONArray jsonArray1=jsonObject.getJSONArray("request");
////                                        Toast.makeText(Staff_order_activity.this, String.valueOf(jsonArray1), Toast.LENGTH_SHORT).show();
////                                        Toast.makeText(Staff_order_activity.this, String.valueOf(jsonArray1.length()), Toast.LENGTH_SHORT).show();
//                                        int i=0;
//                                        while(i<jsonArray1.length())
//                                        {
//                                            JSONObject requestObject = jsonArray1.getJSONObject(i);
//                                            String id_order= requestObject.getString("id_order");
//                                            String name= requestObject.getString("name");
//                                            String email= requestObject.getString("email");
//                                            String addres= requestObject.getString("addres");
//                                            String mobile= requestObject.getString("mobile");
//                                            String order_method= requestObject.getString("order_method");
//                                            String note= requestObject.getString("note");
//                                            String serviceslist= requestObject.getString("serviceslist");
//                                            String time_begin12= requestObject.getString("time_begin");
//                                            String time_finish12= requestObject.getString("time_finish");
//                                            String date12= requestObject.getString("date");
//                                            String staffcuthairList1= requestObject.getString("staffcuthairList");
//                                            String status= requestObject.getString("status");
//                                            String id_saloon= requestObject.getString("id_saloon");
//                                            String payment= requestObject.getString("payment");
//                                            String amount= requestObject.getString("amount");
//
//                                            Gson gson1 = new Gson();
//                                            Type type = new TypeToken<ArrayList<Services>>(){}.getType();
//                                            ArrayList<Services> servicelist = gson1.fromJson(serviceslist, type);
//
//
//                                            Gson gson2 = new Gson();
//                                            Type type1 = new TypeToken<ArrayList<Staffcuthair>>(){}.getType();
//                                            ArrayList<Staffcuthair> staffList1 = gson2.fromJson(staffcuthairList1, type1);
//                                            Request request1 = new Request(name, mobile, email, addres, note, order_method, servicelist, date12, time_begin12, time_finish12, staffList1, id_order,id_saloon, status, payment, Integer.parseInt(amount));
//                                            //  Toast.makeText(Staff_order_activity.this, String.valueOf(servicelist.size()), Toast.LENGTH_SHORT).show();
//                                            String dateso = request1.getDate();
//                                            String time_beginso = request1.getTime_begin();
//                                            String time_finishso = request1.getTime_finish();
//                                            String[] dateso1 = dateso.trim().split("-");
//                                            String[] dateso2 = date.trim().split("-");
//                                            String[] timebeginso1 = time_beginso.trim().split(":");
//                                            String[] timebeginso2 = time_begin.trim().split(":");
//                                            String[] timefinishso1 = time_finishso.trim().split(":");
//                                            String[] timefinishso2 = time_finish.trim().split(":");
////                                            Toast.makeText(Staff_order_activity.this, time_begin, Toast.LENGTH_SHORT).show();
////                                            Toast.makeText(Staff_order_activity.this, time_beginso, Toast.LENGTH_SHORT).show();
//
//                                            if (Integer.parseInt(dateso1[0]) == Integer.parseInt(dateso2[0]) && Integer.parseInt(dateso1[1]) == Integer.parseInt(dateso2[1]) && Integer.parseInt(dateso1[2]) == Integer.parseInt(dateso2[2])) {
//                                                if (Integer.parseInt(timebeginso1[0]) > Integer.parseInt(timefinishso2[0])) {
//                                                } else if (Integer.parseInt(timefinishso1[0]) < Integer.parseInt(timebeginso2[0])) {
//                                                } else if (Integer.parseInt(timefinishso1[0]) == Integer.parseInt(timebeginso2[0]) && Integer.parseInt(timefinishso1[1]) <= Integer.parseInt(timebeginso2[1])) {
//                                                } else if (Integer.parseInt(timebeginso1[0]) == Integer.parseInt(timefinishso2[0]) && Integer.parseInt(timebeginso1[1]) >= Integer.parseInt(timefinishso2[1])) {
//                                                } else {
//
//                                                    for(Staffcuthair staffcuthair:staffList1)
//                                                    { Boolean isreal = false;
//                                                        int index = -10;
//                                                        for(Staffcuthair element : staffchoose1){
//                                                            if(element.getEmail().equals(staffcuthair.getEmail()))
//                                                            {
//                                                                isreal = true;
//                                                                index = staffchoose1.indexOf(element);
//                                                            }
//                                                        }
//                                                        if (isreal) {
//                                                            Toast.makeText(Staff_order_activity.this, "Nhân viên được đăng ký đã bận", Toast.LENGTH_SHORT).show();
//                                                            return;
//                                                        }
//
//                                                    }
//
//
//
//
//
//
//
//                                                }
//
//
//                                            }
//
//                                            i++;
//                                        }
//                                        Request request = new Request(name_staff, mobile_staff, email_staff, address_staff, "", howorder, selectServicesList, date, time_begin, time_finish, staffchoose1, "", Saloonid, "Pending", howpayment, Integer.parseInt(totalmoney));
//
//                                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                                        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
//                                                constants.URL_addrequest,
//                                                new Response.Listener<String>() {
//                                                    @Override
//                                                    public void onResponse(String response) {
////                                                        Toast.makeText(Staff_order_activity.this, "ok", Toast.LENGTH_SHORT).show();
//                                                        try {
//                                                            JSONObject jsonObject = new JSONObject(response);
//                                                            if(!jsonObject.getBoolean("error")) {
//                                                                Toast.makeText(Staff_order_activity.this,"thanhcong", Toast.LENGTH_SHORT).show();
//                                                                Intent intent = new Intent(Staff_order_activity.this, Successmonitoractivity.class);
//                                                                intent.putExtra("name_staff", name_staff);
//                                                                intent.putExtra("email_staff", email_staff);
//                                                                intent.putExtra("mobile_staff", mobile_staff);
//                                                                intent.putExtra("address_staff", address_staff);
//
//                                                                intent.putExtra("howorder", howorder);
//                                                                intent.putExtra("howpayment", howpayment);
//                                                                //       intent.putExtra("selectServicesList",selectServicesList);
//                                                                intent.putExtra("status", status);
//                                                                intent.putExtra("Saloonid", Saloonid);
//                                                                intent.putExtra("Adminid", Adminid);
//                                                                intent.putExtra("Workinghr", Workinghr);
//                                                                intent.putExtra("date", date);
//
//                                                                intent.putExtra("time_begin", time_begin);
//                                                                intent.putExtra("time_finish", time_finish);
//                                                                intent.putExtra("selectServicesList", (Serializable) selectServicesList);
//                                                                intent.putExtra("totalmoney", totalmoney);
//                                                                startActivity(intent);
//
//
//
//                                                            }else{
//                                                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                                                            }
////                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
////                                if(jsonObject.getString("error").equals(false)) {
//////                                        startActivity(new Intent(getApplicationContext(), SaloonDashboard.class));
//////                                        Toast.makeText(getApplicationContext(), " Login Thành công.", Toast.LENGTH_SHORT).show();
//////                                        finish();
////                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
////                                }
//                                                        } catch (JSONException e) {
//                                                            e.printStackTrace();
//                                                        }
//                                                    }
//                                                },
//                                                new Response.ErrorListener() {
//                                                    @Override
//                                                    public void onErrorResponse(VolleyError error) {
//                                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                                                    }
//                                                })
//                                        {
//                                            @Override
//                                            protected Map<String, String> getParams() throws AuthFailureError {
//                                                Gson gson = new Gson();
//                                                String jsonservice = gson.toJson(request.getServices());
//                                                Gson gson1 = new Gson();
//                                                String jsonstaff = gson1.toJson(request.getStaffcuthairList());
//                                                Map<String, String> params = new HashMap<>();
//                                                params.put("name", request.getName());
//                                                params.put("email", request.getEmail());
//                                                params.put("address", request.getAddres());
//                                                params.put("mobile", request.getMobile());
//                                                params.put("order_method", request.getOrder());
//                                                params.put("note", request.getNote());
//                                                params.put("servicelist",jsonservice );
//                                                params.put("timebegin", request.getTime_begin());
//                                                params.put("timefinish", request.getTime_finish());
//                                                params.put("date", request.getDate());
//                                                params.put("staffcuthairlist", jsonstaff);
//                                                params.put("idsalon", request.getId_saloon());
//                                                params.put("status", request.getStatus());
//                                                params.put("payment", request.getPayment());
//                                                params.put("amount",String.valueOf(request.getAmount()) );
//                                                return params;
//                                            }
//                                        };
//
//                                        requestQueue.add(stringRequest);
//
//
//
//
//
//

//
//                                    }else{
//                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                                    }
////                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        })
//                {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//
//                        Map<String, String> params = new HashMap<>();
//
//                        params.put("date", date);
//
//                        return params;
//                    }
//                };
//
//                requestQueue.add(stringRequest);


            }
        });

    }

    public void getlist() {
        RecyclerView recyclerView1;

        int n = Resources.getSystem().getDisplayMetrics().heightPixels;
        //  Toast.makeText(mactivity, String.valueOf(n), Toast.LENGTH_SHORT).show();
        final DialogPlus dialogPlus = DialogPlus.newDialog(Staff_order_activity.this)
                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.customer_list_choose))
                .setExpanded(true, n * 7 / 8)
                .create();
        dialogPlus.show();
        View view = dialogPlus.getHolderView();
        SearchView searchView = view.findViewById(R.id.searchcustomer);
        recyclerView1 = view.findViewById(R.id.customer_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Staff_order_activity.this);
        recyclerView1.setLayoutManager(linearLayoutManager);

        List<Staffcuthair> staffcuthairList;
        staffcuthairList = new ArrayList<>();

        ImageView ic_notfound = view.findViewById(R.id.notfound);
        TextView noservice = view.findViewById(R.id.noservice);


        Call<List<Staffcuthair>> staffcuthairCall = backendapi.getstaffbyid(Saloonid);
        staffcuthairCall.enqueue(new Callback<List<Staffcuthair>>() {
            @Override
            public void onResponse(Call<List<Staffcuthair>> call, retrofit2.Response<List<Staffcuthair>> response) {

                staffcuthairList.addAll(response.body());


                Call<List<Request>> call1 = backendapi.getlistsrequestdate(date);
                call1.enqueue(new Callback<List<Request>>() {
                    @Override
                    public void onResponse(Call<List<Request>> call, retrofit2.Response<List<Request>> response) {

                        List<Request> requestList = new ArrayList<>();
                        requestList.addAll(response.body());
                        for (Request request1 : requestList) {

                            String dateso = request1.getDate();
                            String time_beginso = request1.getTime_begin();
                            String time_finishso = request1.getTime_finish();
                            String[] dateso1 = dateso.trim().split("-");
                            String[] dateso2 = date.trim().split("-");
                            String[] timebeginso1 = time_beginso.trim().split(":");
                            String[] timebeginso2 = time_begin.trim().split(":");
                            String[] timefinishso1 = time_finishso.trim().split(":");
                            String[] timefinishso2 = time_finish.trim().split(":");
                            String staffcuthairList1 = request1.getJsonlist();

                            Gson gson2 = new Gson();
                            Type type1 = new TypeToken<ArrayList<Staffcuthair>>() {
                            }.getType();
                            ArrayList<Staffcuthair> staffList1 = gson2.fromJson(staffcuthairList1, type1);
                            request1.setStaffcuthairList(staffList1);

                            if (Integer.parseInt(dateso1[0]) == Integer.parseInt(dateso2[0]) && Integer.parseInt(dateso1[1]) == Integer.parseInt(dateso2[1]) && Integer.parseInt(dateso1[2]) == Integer.parseInt(dateso2[2])) {
                                if (Integer.parseInt(timebeginso1[0]) > Integer.parseInt(timefinishso2[0])) {
                                } else if (Integer.parseInt(timefinishso1[0]) < Integer.parseInt(timebeginso2[0])) {
                                } else if (Integer.parseInt(timefinishso1[0]) == Integer.parseInt(timebeginso2[0]) && Integer.parseInt(timefinishso1[1]) <= Integer.parseInt(timebeginso2[1])) {
                                } else if (Integer.parseInt(timebeginso1[0]) == Integer.parseInt(timefinishso2[0]) && Integer.parseInt(timebeginso1[1]) >= Integer.parseInt(timefinishso2[1])) {
                                } else {

                                    for (Staffcuthair staffcuthair : request1.getStaffcuthairList()) {
                                        Boolean isreal = false;
                                        int index = -10;
                                        for (Staffcuthair element : staffcuthairList) {
                                            if (element.getEmail().equals(staffcuthair.getEmail())) {
                                                isreal = true;
                                                index = staffcuthairList.indexOf(element);
                                            }
                                        }
                                        if (isreal) {
                                            staffcuthairList.remove(index);
                                        }

                                    }


                                }


                            }


                        }


                        Staff_order_adapter staff_order_adapter = new Staff_order_adapter(staffcuthairList, new staffchooseorder() {
                            @Override
                            public void staffchoose(Staffcuthair staffcuthair1) {

                                if (staffchoose1.size() == 0) {
                                    staffchoose1.add(staffcuthair1);
                                } else {
                                    Boolean exist = true;
                                    for (Staffcuthair element : staffchoose1) {

                                        if (element.getEmail().equals(staffcuthair1.getEmail())) {
                                            exist = false;
                                        }


                                    }
                                    if (exist) {
                                        staffchoose1.add(staffcuthair1);
                                    }
                                }


                                adapterbabe = new Staff_order_adapter_show(staffchoose1, new staffchooseorder() {
                                    @Override
                                    public void staffchoose(Staffcuthair staffcuthair) {


                                    }
                                }, getApplicationContext());

                                rec2.setLayoutManager(new LinearLayoutManager(Staff_order_activity.this));
                                rec2.setAdapter(adapterbabe);
                                adapterbabe.notifyDataSetChanged();


                                dialogPlus.dismiss();


                            }
                        });
                        recyclerView1.setAdapter(staff_order_adapter);

                        if (staffcuthairList.size() == 0) {
                            noservice.setVisibility(TextView.VISIBLE);
                            ic_notfound.setVisibility(View.VISIBLE);
                            recyclerView1.setVisibility(TextView.INVISIBLE);
                            Toast.makeText(Staff_order_activity.this, "No Past Orders available", Toast.LENGTH_SHORT).show();
                        }
                        staff_order_adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onFailure(Call<List<Request>> call, Throwable t) {
                        Toast.makeText(Staff_order_activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("faie", t.getMessage());
                    }
                });


            }

            @Override
            public void onFailure(Call<List<Staffcuthair>> call, Throwable t) {
                Toast.makeText(Staff_order_activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("faie", t.getMessage());
            }
        });


        dialogPlus.getHolderView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialogPlus.dismiss();
                    return true;
                }
                return false;
            }
        });

    }

    private void getinit() {
        rec2 = findViewById(R.id.staffrecycldeviewshow);
        btn_submit = findViewById(R.id.btn_submit_order);
        btn_back = findViewById(R.id.btn_back_order);
        staffchoose1 = new ArrayList<Staffcuthair>();
        btn_select = findViewById(R.id.btn_select_staff);

    }

    String totalmoney;

    private void getintentdata(Intent intent) {
        name_staff = intent.getStringExtra("name_staff");
        email_staff = intent.getStringExtra("email_staff");
        mobile_staff = intent.getStringExtra("mobile_staff");
        address_staff = intent.getStringExtra("address_staff");
        status = intent.getStringExtra("status");
        Saloonid = intent.getStringExtra("Saloonid");
        Adminid = intent.getStringExtra("Adminid");
        howorder = intent.getStringExtra("howorder");
        howpayment = intent.getStringExtra("howpayment");
        name_staff = intent.getStringExtra("name_staff");
        Workinghr = intent.getStringExtra("Workinghr");

        date = intent.getStringExtra("date");
        time_begin = intent.getStringExtra("time_begin");
        time_finish = intent.getStringExtra("time_finish");
        totalmoney = intent.getStringExtra("totalmoney");

        selectServicesList = (ArrayList<Services>) intent.getSerializableExtra("selectServicesList");


    }


}