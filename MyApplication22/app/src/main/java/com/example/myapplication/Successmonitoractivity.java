
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Model.Services;
import com.example.myapplication.adapter.Servicesadapter;
import com.example.myapplication.adapter.Servicesadapter1;
import com.example.myapplication.viewModel.BookUserViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class Successmonitoractivity extends AppCompatActivity {
    String totalmoney;
    String name_staff, email_staff, Workinghr;
    ArrayList<Services> selectServicesList;
    String mobile_staff;
    String address_staff;
    final Calendar c = Calendar.getInstance();
    String status, Saloonid, Adminid;
    String howorder, howpayment;
    String date, time_begin, time_finish;
    Button btn_appoiment, btn_homel;
    RecyclerView rec2;

    Staff_order_activity mactivity2;
   TextView txtusername, txtmobileuser, txtemailuser, txtaddressuser;
    TextView tvpayment, tvorder, tvtimebegin, tvtimefinish, tvdate, totalamt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sucess_monitor);
        getinit();
        getintentdata(getIntent());


        txtusername.setText(name_staff);
        txtmobileuser.setText(mobile_staff);
        txtaddressuser.setText(address_staff);
        txtemailuser.setText(email_staff);
        tvorder.setText(howorder);
        tvpayment.setText(howpayment);
        tvdate.setText(date);
        tvtimefinish.setText(time_finish);
        tvtimebegin.setText(time_begin);
        totalamt.setText(String.valueOf(totalmoney));

        rec2.setLayoutManager(new LinearLayoutManager(Successmonitoractivity.this));
        Servicesadapter1 adapter = new Servicesadapter1(selectServicesList); //cái dịch vụ
        adapter.notifyDataSetChanged();
        rec2.setAdapter(adapter);


        btn_homel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Successmonitoractivity.this, Userdashboardactivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

    }

    private void getinit() {
        txtusername = findViewById(R.id.txtnameuser);
        txtmobileuser = findViewById(R.id.txtmobileuser);
        txtemailuser = findViewById(R.id.txtemailuser);
        txtaddressuser = findViewById(R.id.txtaddressuser);
        tvorder = findViewById(R.id.txtorderuser);
        tvpayment = findViewById(R.id.txtpaymentuser);
        tvdate = findViewById(R.id.txtdate);
        tvtimebegin = findViewById(R.id.txttime_begin);
        tvtimefinish = findViewById(R.id.txttime_finish);
        btn_appoiment = findViewById(R.id.btn_appoit_order);
        btn_homel = findViewById(R.id.btn_home_order);
        rec2 = findViewById(R.id.ServicerecyclerView);
        totalamt = findViewById(R.id.totalamt);


    }

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
