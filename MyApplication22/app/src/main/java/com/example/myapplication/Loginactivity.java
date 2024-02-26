package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;



import com.example.myapplication.Model.User;

import com.example.myapplication.viewModel.LoginViewModel;
import com.example.myapplication.databinding.LoginActivityBinding;


public class Loginactivity extends AppCompatActivity {


private LoginActivityBinding loginActivityBinding;
    User user;
    MainActivityClickHandler mainActivityClickHandler;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        LoginActivityBinding loginActivityBinding= DataBindingUtil.setContentView(this, R.layout.login_activity);
       LoginViewModel loginViewModel=new LoginViewModel(getApplicationContext());
        loginActivityBinding.setLoginViewModel(loginViewModel);
        loginActivityBinding.setLifecycleOwner(this);
        EditText pass= findViewById(R.id.editTextPassword);
      CheckBox checkBoxShowPassword = findViewById(R.id.checkBoxShowPassword);


        checkBoxShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Khi trạng thái của CheckBox thay đổi, ẩn/hiện mật khẩu trong EditText
                if (isChecked) {
                    pass.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    pass.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        loginViewModel.getNavigateToNextScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldNavigate) {
                if (shouldNavigate) {
                    // Thực hiện chuyển màn hình ở đây
                    Intent intent = new Intent(getApplicationContext(), Registeractivity.class);
                    startActivity(intent);

                    // Đặt giá trị về false để tránh việc chuyển màn hình lại khi LiveData thay đổi lần nữa

                }
            }
        });

        loginViewModel.getNavigateToNextHomeScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldNavigate) {
                if (shouldNavigate) {
                    // Thực hiện chuyển màn hình ở đây
                    Toast.makeText(Loginactivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Userdashboardactivity.class);
                    startActivity(intent);

                    // Đặt giá trị về false để tránh việc chuyển màn hình lại khi LiveData thay đổi lần nữa

                }
            }
        });


       TextView forgotpass=loginActivityBinding.getRoot().findViewById(R.id.forgotpass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(Loginactivity.this, ForgotPasswordActivity.class);

                startActivity(intent);


            }
        });


    }

    public class MainActivityClickHandler{
        Context context;

        public MainActivityClickHandler(Context context) {
            this.context = context;
        }

        public void clickButton(View view){
            user.setEmail("uuuuuu");

        }
    }


}