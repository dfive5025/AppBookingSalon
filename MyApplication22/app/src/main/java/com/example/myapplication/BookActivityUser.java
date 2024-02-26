package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.myapplication.Model.Saloon;
import com.example.myapplication.databinding.ActivityBookBinding;
import com.example.myapplication.databinding.LoginActivityBinding;
import com.example.myapplication.viewModel.BookUserViewModel;

import java.io.Serializable;

public class BookActivityUser extends AppCompatActivity {
    ActivityBookBinding binding;
    String howorder, howpayment;

    private static final String[] order = {"Direct", "Call", "Social Media"};
    private static final String[] payment = {"Cash", "ATM", "Internet Banking"};
    Saloon saloon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getintentdata();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_book);
        View view = binding.getRoot();
        BookUserViewModel bookUserViewModel = new BookUserViewModel(saloon, this, view, this);
        binding.setBookusermodel(bookUserViewModel);
        binding.setLifecycleOwner(this);
        Spinner spiner = findViewById(R.id.spinnerorder);
        Spinner spinner2 = findViewById(R.id.spinnerpayment);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, order);
        int spinerselect = adapter.getPosition(order[1]);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(adapter);
        spiner.setSelection(spinerselect);
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //    Toast.makeText(context, areas[i], Toast.LENGTH_SHORT).show();
                howorder = order[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, payment);
        int spinerselect12 = adapter1.getPosition(payment[1]);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        spinner2.setSelection(spinerselect12);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //    Toast.makeText(context, areas[i], Toast.LENGTH_SHORT).show();
                howpayment = payment[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bookUserViewModel.getNavigateToNextScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldNavigate) {
                if (shouldNavigate) {
                    // Thực hiện chuyển màn hình ở đây
                    Intent intent = new Intent(getApplicationContext(), Timeorderactivity.class);
                    intent.putExtra("name_staff", bookUserViewModel.name.getValue().toString().trim());
                    intent.putExtra("email_staff", bookUserViewModel.email.getValue().toString().trim());
                    intent.putExtra("mobile_staff", bookUserViewModel.mobile.getValue().toString().trim());
                    intent.putExtra("address_staff", bookUserViewModel.address.getValue().toString().trim());
                    intent.putExtra("howorder", howorder);
                    intent.putExtra("howpayment", howpayment);
                    intent.putExtra("status", "Pending");
                    intent.putExtra("selectServicesList", (Serializable) bookUserViewModel.servicesList);
                    intent.putExtra("Saloonid", saloon.getId_saloon());
                    intent.putExtra("Adminid", saloon.getId_AdminSaloons());
                    intent.putExtra("Workinghr", saloon.getWorkingHr());
                    intent.putExtra("totalmoney", bookUserViewModel.totalmoneyString.getValue());


                    startActivity(intent);


                    // Đặt giá trị về false để tránh việc chuyển màn hình lại khi LiveData thay đổi lần nữa

                }
            }
        });
    }


    public void onback() {
        onBackPressed();
    }

    public void getintentdata() {
        Intent intent = getIntent();
        saloon = (Saloon) intent.getSerializableExtra("mysaloon");

    }
}