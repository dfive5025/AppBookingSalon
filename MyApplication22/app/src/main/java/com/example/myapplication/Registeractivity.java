package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.myapplication.Model.User;

import com.example.myapplication.databinding.RegisterActivityBinding;
import com.example.myapplication.viewModel.RegisterViewModel;


public class Registeractivity extends AppCompatActivity {


    RegisterActivityBinding registerActivityBinding;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        registerActivityBinding = DataBindingUtil.setContentView(this, R.layout.register_activity);
        RegisterViewModel registerViewModel = new RegisterViewModel(getApplicationContext());
        registerActivityBinding.setRegisterViewModel(registerViewModel);
        registerActivityBinding.setLifecycleOwner(this);


        registerViewModel.getNavigateToNextScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldNavigate) {
                if (shouldNavigate) {
                    // Thực hiện chuyển màn hình ở đây
                    onBackPressed();

                    // Đặt giá trị về false để tránh việc chuyển màn hình lại khi LiveData thay đổi lần nữa

                }
            }
        });


    }


}