package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Model.Services;
import com.example.myapplication.databinding.ActivityOrderTimeBinding;
import com.example.myapplication.databinding.LoginActivityBinding;
import com.example.myapplication.viewModel.TimeorderbookViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Timeorderactivity extends AppCompatActivity {
    String name_staff, email_staff, Workinghr;
    List<Services> selectServicesList;
    String mobile_staff;
    String address_staff;
    String totalmoney;

    final Calendar c = Calendar.getInstance();
    String status, Saloonid, Adminid;
    String howorder, howpayment;
    Button btn_date, btn_time_begin, btn_time_finish, btn_next;
    ImageView btn_back;
    List<Services> services = new ArrayList<Services>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getintentdata(getIntent());

        ActivityOrderTimeBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_order_time);
        TimeorderbookViewModel timeorderbookViewModel=new TimeorderbookViewModel(this,Workinghr,this);
        binding.setViewModel(timeorderbookViewModel);
        binding.setLifecycleOwner(this);


        timeorderbookViewModel.getNavigateToNextScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldNavigate) {
                if (shouldNavigate) {
                    // Thực hiện chuyển màn hình ở đây
                    Intent intent = new Intent(Timeorderactivity.this, Staff_order_activity.class);
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
                    intent.putExtra("date", timeorderbookViewModel.timedate.getValue());

                    intent.putExtra("time_begin", timeorderbookViewModel.timebegin.getValue());
                    intent.putExtra("time_finish", timeorderbookViewModel.timefinish.getValue());
                    intent.putExtra("selectServicesList", (Serializable) selectServicesList);
                    intent.putExtra("totalmoney", totalmoney);
                    startActivity(intent);






                    // Đặt giá trị về false để tránh việc chuyển màn hình lại khi LiveData thay đổi lần nữa

                }
            }
        });



       Button btn_shedule=findViewById(R.id.btn_shedule);
        btn_shedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Timeorderactivity.this, Workscheduleactivity.class);

                intent.putExtra("Saloon_id", Saloonid);

                startActivity(intent);


            }
        });


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
        totalmoney = intent.getStringExtra("totalmoney");

        selectServicesList = (List<Services>) intent.getSerializableExtra("selectServicesList");

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}