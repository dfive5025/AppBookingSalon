package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.constants.constants;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {


    private EditText inputEmail,inputOTP;

    private Button btn_sendOTP, btnBack,btn_resend,btn_submit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.forgot_password);

        inputEmail = (EditText) findViewById(R.id.email);
        inputOTP = (EditText) findViewById(R.id.otp);
        btn_sendOTP = (Button) findViewById(R.id.btn_sendOTP);
        btn_resend = findViewById(R.id.btn_Resend);
        btnBack = (Button) findViewById(R.id.btn_back);
        btn_submit = findViewById(R.id.btn_submit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        btn_sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputEmail.getText().toString().trim().matches(emailPattern)){
                    btn_sendOTP.setText("ReSend");
                    btn_sendOTP.setBackgroundColor(getResources().getColor(R.color.blue));
                    onClickSendOTP(inputEmail.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(),"Nhập lại email nhé !", Toast.LENGTH_LONG).show();

                }
//                btn_sendOTP.setVisibility(View.GONE);
//                btn_resend.setVisibility(View.VISIBLE);

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputEmail.getText().toString().trim().matches(emailPattern)&&inputOTP.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Nhập lại mã OTP !", Toast.LENGTH_LONG).show();
                }else {
                    check_otp(inputEmail.getText().toString(), inputOTP.getText().toString());
                }
            }
        });
    }
    public void onClickSendOTP(String Email){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                constants.URL_sendOTP1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"Kiểm Tra Gmail Của Bạn Và Nhập OTP vào ô bên dưới !", Toast.LENGTH_LONG).show();
//                                btn_sendOTP.setEnabled(false);
//                                inputEmail.setEnabled(false);


                            }else{
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
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("email",inputEmail.getText().toString() );

                return params;
            }
        };

        requestQueue.add(stringRequest);

    }
    public void check_otp(String email,String otp){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                constants.URL_checkotp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"Đổi Mật Khẩu Mới", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ForgotPasswordActivity.this,UpdatePasswordActivity.class);
                                intent.putExtra("email",inputEmail.getText().toString());
                                startActivity(intent);



                                //btn_sendOTP.setEnabled(false);
//                                inputEmail.setEnabled(false);


                            }else{
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
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("otp",inputOTP.getText().toString() );
                params.put("email",inputEmail.getText().toString() );
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }
    @Override
    protected void onStop() {
        super.onStop();
//        myRef.removeEventListener(myListener);
    }
}
