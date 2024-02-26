
package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Model.Request;
import com.example.myapplication.Model.Services;
import com.example.myapplication.Model.Staffcuthair;
import com.example.myapplication.adapter.WorkScheduleadapter;
import com.example.myapplication.constants.constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkscheduleaOfeachStaffctivity extends AppCompatActivity {
    Staffcuthair staff1;
    RecyclerView workschedule;
    Button date_schedule;
    ImageView back_schedule;
    String saloon_id;
    String salaryFromOrders;
    String CountBill;
    WorkScheduleadapter scheduleadapter;
    TextView name;
    TextView sdt;
    TextView number_orders;
    TextView salary_from_orders;
    TextView TotalSalary;
    List<Request> requests = new ArrayList<Request>();
    final Calendar c = Calendar.getInstance();
    ImageView ic_notfound;
    TextView noservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workscheduleactivity_staff);
        CountBill = getIntent().getStringExtra("count");
        staff1 = (Staffcuthair) getIntent().getSerializableExtra("staff1");
        salaryFromOrders = getIntent().getStringExtra("salaryFromOrders");
        getinit();
//        totalLOUT.setVisibility(View.VISIBLE);
        getintent(getIntent());
        String date = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());


        date_schedule.setText(date);
        getdata(date);
        setevent();
    }

    private void getinit() {
        workschedule = findViewById(R.id.list_work_schedule);
        date_schedule = findViewById(R.id.btn_date_workschedule);
        back_schedule = findViewById(R.id.btn_work_back);
        noservice = findViewById(R.id.noservice);
        ic_notfound = findViewById(R.id.notfound);
        name = findViewById(R.id.name);
        sdt = findViewById(R.id.sdt);
        number_orders = findViewById(R.id.number_orders);
        salary_from_orders = findViewById(R.id.salary_from_orders);
        TotalSalary = findViewById(R.id.TotalSalary);
        name.setText(staff1.getName());
        sdt.setText(staff1.getMobile());
        number_orders.setText(String.valueOf(CountBill));
        if (salaryFromOrders.equals("")) {
            salaryFromOrders = "0";
        }
        String a = salaryFromOrders.replaceAll("[^0-9]", "");
//        Toast.makeText(WorkscheduleaOfeachStaffctivity.this,"là" + salaryFromOrders,Toast.LENGTH_SHORT).show();

    }

    private void getintent(Intent intent) {
//        String loginValue = AppUtils.getStringValue(WorkscheduleaOfeachStaffctivity.this, Constant.LoginKey);
//        if(loginValue.equals(Constant.SALOON)) {
        saloon_id = intent.getStringExtra("Saloon_id");
//        } else if(loginValue.equals(Constant.ADMINSALOONS)) {
//            s = intent.getParcelableExtra("staff1");
//            saloon_id = s.getIdsaloon();
//            }
    }

    private void getdata(String date1) {
        if (staff1.getIdsaloon() == null) {
            return;
        }
        ArrayList<Request> Requests = new ArrayList<Request>();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                constants.URL_getrequestbyidsalon,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {


                                JSONArray jsonArray1 = jsonObject.getJSONArray("request");

                                int i = 0;
                                while (i < jsonArray1.length()) {
                                    JSONObject requestObject = jsonArray1.getJSONObject(i);
                                    String id_order = requestObject.getString("id_order");
                                    String name = requestObject.getString("name");
                                    String email = requestObject.getString("email");
                                    String addres = requestObject.getString("addres");
                                    String mobile = requestObject.getString("mobile");
                                    String order_method = requestObject.getString("order_method");
                                    String note = requestObject.getString("note");
                                    String serviceslist = requestObject.getString("serviceslist");
                                    String time_begin = requestObject.getString("time_begin");
                                    String time_finish = requestObject.getString("time_finish");
                                    String date = requestObject.getString("date");
                                    String staffcuthairList1 = requestObject.getString("staffcuthairList");
                                    String status = requestObject.getString("status");
                                    String id_saloon = requestObject.getString("id_saloon");
                                    String payment = requestObject.getString("payment");
                                    String amount = requestObject.getString("amount");

                                    Gson gson1 = new Gson();
                                    Type type = new TypeToken<ArrayList<Services>>() {
                                    }.getType();
                                    ArrayList<Services> servicelist = gson1.fromJson(serviceslist, type);


                                    Gson gson2 = new Gson();
                                    Type type1 = new TypeToken<ArrayList<Staffcuthair>>() {
                                    }.getType();
                                    ArrayList<Staffcuthair> staffList1 = gson2.fromJson(staffcuthairList1, type1);
                                    for (Staffcuthair s1 : staffList1) {
                                        if (s1.getIdstaff().equals(staff1.getIdstaff())) {
                                            Request request1 = new Request(name, mobile, email, addres, note, order_method, servicelist, date, time_begin, time_finish, staffList1, id_order, id_saloon, status, payment, Integer.parseInt(amount));
                                            //  Toast.makeText(Staff_order_activity.this, String.valueOf(servicelist.size()), Toast.LENGTH_SHORT).show();
                                            String dateso = request1.getDate();
//                    Toast.makeText(Workscheduleactivity.this, dateso, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Workscheduleactivity.this, date, Toast.LENGTH_SHORT).show();


                                            String[] dateso1 = dateso.trim().split("-");
                                            String[] dateso2 = date1.trim().split("-");
                                            if (Integer.parseInt(dateso1[0]) == Integer.parseInt(dateso2[0]) && Integer.parseInt(dateso1[1]) == Integer.parseInt(dateso2[1]) && Integer.parseInt(dateso1[2]) == Integer.parseInt(dateso2[2])) {
//                        Toast.makeText(Workscheduleactivity.this, "Vao day", Toast.LENGTH_SHORT).show();
                                                Requests.add(request1);
                                            }
//                                            if (status.equals("Paid")) {
//                                                    CountBill++;
//                                            }
                                        }
                                    }
//                                    Request request1 = new Request(name, mobile, email, addres, note, order_method, servicelist, date, time_begin, time_finish, staffList1, id_order,id_saloon, status, payment, Integer.parseInt(amount));
//                                    //  Toast.makeText(Staff_order_activity.this, String.valueOf(servicelist.size()), Toast.LENGTH_SHORT).show();
//                                    String dateso = request1.getDate();
////                    Toast.makeText(Workscheduleactivity.this, dateso, Toast.LENGTH_SHORT).show();
////                    Toast.makeText(Workscheduleactivity.this, date, Toast.LENGTH_SHORT).show();
//
//
//                                    String[] dateso1 = dateso.trim().split("-");
//                                    String[] dateso2 = date1.trim().split("-");
//                                    if (Integer.parseInt(dateso1[0]) == Integer.parseInt(dateso2[0]) && Integer.parseInt(dateso1[1]) == Integer.parseInt(dateso2[1]) && Integer.parseInt(dateso1[2]) == Integer.parseInt(dateso2[2])) {
////                        Toast.makeText(Workscheduleactivity.this, "Vao day", Toast.LENGTH_SHORT).show();
//                                        Requests.add(request1);
//                                    }

                                    i++;
                                }
                                scheduleadapter = new WorkScheduleadapter(WorkscheduleaOfeachStaffctivity.this, Requests);

                                workschedule.setAdapter(scheduleadapter);
                                workschedule.setLayoutManager(new LinearLayoutManager(WorkscheduleaOfeachStaffctivity.this, RecyclerView.VERTICAL, false));
                                scheduleadapter.notifyDataSetChanged();

                                if (Requests.size() == 0) {
                                    noservice.setText("Employee No Schedule On This Day");
                                    noservice.setVisibility(TextView.VISIBLE);
                                    ic_notfound.setVisibility(View.VISIBLE);
                                } else {
                                    noservice.setVisibility(View.GONE);
                                    ic_notfound.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
//                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("idsalon", staff1.getIdsaloon());

                return params;
            }
        };

        requestQueue.add(stringRequest);


    }


    private void setevent() {

        back_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        date_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(WorkscheduleaOfeachStaffctivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                final Calendar c = Calendar.getInstance();
                                String date = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());

                                date_schedule.setText(dayOfMonth + "-" + (monthOfYear) + "-" + year);

                                getdata(date_schedule.getText().toString());
                                //   Toast.makeText(Time_order_activity.this, btn_date.getText(), Toast.LENGTH_SHORT).show();
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();


            }
        });

    }


}
