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

public class UpdatePasswordActivity extends AppCompatActivity {


    private EditText inputPass,inputRepass;

    private Button btn_sendOTP, btnBack,btn_resend,btn_submit;

   private String email;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.update_password);
        email  = getIntent().getStringExtra("email");
        inputPass = (EditText) findViewById(R.id.password);
        inputRepass = (EditText) findViewById(R.id.rePass);
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
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputPass1 = inputPass.getText().toString().trim();
                String inputRepass1 = inputRepass.getText().toString().trim();
                if(inputPass1.equals(inputRepass1)) {
                    if (inputPass1.matches(PASSWORD_PATTERN) && inputPass1.length() > 8) {
                        if (inputPass.getText().toString().trim().matches(emailPattern) && inputRepass.getText().toString().trim().equals("")) {
                            Toast.makeText(getApplicationContext(), "Nhập lại mã OTP !", Toast.LENGTH_LONG).show();
                        } else {
                            update_pass(inputPass.getText().toString(), email);
                            Intent intent = new Intent(UpdatePasswordActivity.this,Loginactivity.class);
                            startActivity(intent);

                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Mật khẩu chưa đủ mạnh !", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Mật khẩu mới và cũ không giống nhau", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void update_pass(String pass,String email){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                constants.URL_UpdatePassuser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"Đổi Mật Khẩu Mới Thành Công", Toast.LENGTH_LONG).show();




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

                params.put("password",pass );
                params.put("email",email );
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
