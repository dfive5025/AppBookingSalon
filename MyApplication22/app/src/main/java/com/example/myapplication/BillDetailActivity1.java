
package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Request;
import com.example.myapplication.Model.ResponseModel;
import com.example.myapplication.Model.Services;
import com.example.myapplication.adapter.Servicesadapter;
import com.example.myapplication.adapter.Servicesadapter1;
import com.example.myapplication.repository.RetrofitInstance;
import com.example.myapplication.repository.RetrofitInterface;

import com.example.myapplication.utils.AppUtils;
import com.example.myapplication.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class BillDetailActivity1 extends AppCompatActivity {
    int totalmoney;
    String idorder, email_staff, Workinghr;
    ArrayList<Services> selectServicesList;
    String mobile_staff;
    String address_staff;
    final Calendar c = Calendar.getInstance();
    String status, Saloonid, Adminid;
    String loginKey;
    String loginId;
    String howorder, howpayment;
    String date, time_begin, time_finish;
    Button btn_appoiment, btn_homel;
    RecyclerView rec2;
    Request request;
   TextView txtusername, txtmobileuser, txtemailuser, txtaddressuser;
    TextView tvpayment, tvorder, tvtimebegin, tvtimefinish, tvdate, totalamt, txtnote, statustitle;
    Button btnInPosition, btnDone, btnCancel;
    ImageButton btnDeleteBill;
    LinearLayout llOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appoiment_bill_detail1);
         ImageView iconstatus  = findViewById(R.id.icon_status);
        getinit();
        getintentdata(getIntent());

        getInfoOrder(idorder, getIntent());
//        TextView statustitle = findViewById(R.id.tv_status);
//        statustitle.setText("Status: " + status);
//
//        TextView idbill = findViewById(R.id.idbill);
//        idbill.setText("Bill "+"#"+request.getId_order());
//
//        ImageView iconstatus  = findViewById(R.id.icon_status);
//        if (status.equals("Cancel")){
//            iconstatus.setImageResource(R.drawable.icon_cancel);
//        }
//        txtusername.setText("name_staff");
//        txtmobileuser.setText(mobile_staff);
//        txtaddressuser.setText(address_staff);
//        txtemailuser.setText(email_staff);
//        tvorder.setText(howorder);
//        tvpayment.setText(howpayment);
//        tvdate.setText(date);
//        tvtimefinish.setText(time_finish);
//        tvtimebegin.setText(time_begin);
//        totalamt.setText(AppUtils.convertMoney(totalmoney));

//        rec2.setLayoutManager(new LinearLayoutManager(BillDetailActivity1.this));
//        ServiceAdapter adapter = new ServiceAdapter(BillDetailActivity1.this, selectServicesList, "aap", null, null, "saloonBook"); //cái dịch vụ
//        adapter.notifyDataSetChanged();
//        rec2.setAdapter(adapter);

        ImageView btnback = findViewById(R.id.btnbackService);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        btn_appoiment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////
////                Intent intent = new Intent(BillDetail_activity.this, AppoitmentActivity.class);
////                String saloonname = AppUtils.getStringValue(getApplicationContext(), Constant.NAME_SALON);
////                intent.putExtra("slname1", saloonname);
////                intent.putExtra("openformFromWhere","openApoitmentFromSuccessBook");
////                intent.putExtra("idsaloon12",Saloonid);
////                startActivity(intent);
//
//
//
//
//            }
//        });

        btn_homel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                request.setStatus("Cancel");

                //mDatabase.child("R_Request").child(request.getUserID()).child(request.getKey()).setValue(request);
//                holder.btnDone.setVisibility(View.GONE);
//                holder.btnCancel.setText("Cancel");

                LayoutInflater li = LayoutInflater.from(v.getRootView().getContext());
                View promptsView = li.inflate(R.layout.rectime, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        v.getRootView().getContext());
                alertDialogBuilder.setTitle("Lý do:");
                alertDialogBuilder.setMessage("Enter cancellation reason");
                // set alert_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText rect = (EditText) promptsView.findViewById(R.id.etrectime);
                //alertDialog.setView(input);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(alertDialogBuilder.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                request.setStatus("Cancel");
                                request.setNote(rect.getText().toString());
                                statustitle.setText("Status: Cancel");;
                                txtnote.setText(rect.getText().toString());

//                                holder.btnCancel.setVisibility(View.VISIBLE);
                                btnDone.setVisibility(View.GONE);
                                btnInPosition.setVisibility(View.GONE);
                                btnCancel.setText("Customer Canceled");
                                btnCancel.setEnabled(false);
                                iconstatus.setImageResource(R.drawable.icon_cancel);
                                setStatuss(request);

                                //Toast.makeText(context.getApplicationContext(), "Entered: "+rect.getText().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

            }
        });
    }

    private void getInfoOrder(String idorder, Intent intent) {
       String idorder1= intent.getStringExtra("id_order");
        Log.d("idorder", idorder);
        RetrofitInterface retrofitInterface = RetrofitInstance.getService();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", "");
        RetrofitInstance.setToken(token);
        Call<List<Request>> calls = retrofitInterface.getRequestsByidorder(idorder1);
        calls.enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, retrofit2.Response<List<Request>> response) {
                if (response.isSuccessful()) {
                    // Phản hồi thành công, xử lý dữ liệu ở đây
                    List<Request> requestModels = response.body();
                    if (requestModels.size() != 0) {
                        // Lấy thông tin từ requestModel và xử lý
                        // Ví dụ:
                        request = requestModels.get(0);

                        statustitle.setText("Status: " + request.getStatus());

                        TextView idbill = findViewById(R.id.idbill);
                        idbill.setText("Bill "+"#"+request.getId_order());

                        ImageView iconstatus  = findViewById(R.id.icon_status);

                        txtusername.setText(request.getName());
                        txtmobileuser.setText(request.getMobile());
                        txtaddressuser.setText(request.getAddres());
                        txtemailuser.setText(request.getEmail());
                        txtnote.setText(request.getNote());
                        tvorder.setText("call");
                        tvpayment.setText(request.getPayment());
                        tvdate.setText(request.getDate());
                        tvtimefinish.setText(request.getTime_finish());
                        tvtimebegin.setText(request.getTime_begin());
                        totalamt.setText(AppUtils.convertMoney(request.getAmount()));

                        String serviceslist = request.getJsonlistservice();
                        Gson gson1 = new Gson();
                        Type type = new TypeToken<ArrayList<Services>>() {
                        }.getType();
                        ArrayList<Services> servicelist = gson1.fromJson(serviceslist, type);
                        rec2.setLayoutManager(new LinearLayoutManager(BillDetailActivity1.this));
                        Servicesadapter1 adapter = new Servicesadapter1(servicelist); //cái dịch vụ
                        adapter.notifyDataSetChanged();
                        rec2.setAdapter(adapter);

                        // Tiếp tục xử lý thông tin theo yêu cầu của bạn
                        if (request.getStatus().equalsIgnoreCase("Pending")) {
                            btnDone.setVisibility(View.GONE);
                            btnInPosition.setVisibility(View.GONE);
                            btnCancel.setVisibility(View.VISIBLE);
                            iconstatus.setImageResource(R.drawable.pending);
                        }
                        else if (request.getStatus().equalsIgnoreCase("InPosition")) {
                            btnInPosition.setVisibility(View.VISIBLE);
                            btnCancel.setVisibility(View.GONE);
                            btnInPosition.setEnabled(false);
                            iconstatus.setImageResource(R.drawable.ic_success123);
                        }
                        else if (request.getStatus().equalsIgnoreCase("Paid")) {
                            btnDone.setVisibility(View.VISIBLE);
                            btnCancel.setVisibility(View.GONE);
                            btnInPosition.setVisibility(View.GONE);
                            btnDone.setText("Customer Paid");
                            btnDone.setEnabled(false);
                            iconstatus.setImageResource(R.drawable.ic_success123);

                        } else if (request.getStatus().equalsIgnoreCase("Cancel")) {
                            btnCancel.setVisibility(View.VISIBLE);
                            btnDone.setVisibility(View.GONE);
                            btnInPosition.setVisibility(View.GONE);
                            btnCancel.setText("Customer Canceled");
                            btnCancel.setEnabled(false);
                            iconstatus.setImageResource(R.drawable.icon_cancel);
                        }
                    } else {
                        // Đối tượng requestModel null, xử lý theo nhu cầu của bạn
                    }
                } else {
                    // Phản hồi không thành công, xử lý theo nhu cầu của bạn
                    Toast.makeText(getApplicationContext(), "Request not successful: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                // Lỗi xảy ra trong quá trình gửi yêu cầu
                Toast.makeText(getApplicationContext(), "lỗi111" + t.toString(), Toast.LENGTH_LONG).show();
                Log.d("loi",t.toString());
            }
        });
    }


    public void setStatuss(Request requ) {
            RetrofitInterface retrofitInterface = RetrofitInstance.getService();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("Token", "");
            int iduser = sharedPreferences.getInt("iduser", 0);
            RetrofitInstance.setToken(token);
            String role = "42g34g";
            Call<ResponseModel> calls = retrofitInterface.updateRequest(iduser, requ.getId_order(), requ.getStatus(), requ.getNote(),role );
            calls.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                    if (!Objects.requireNonNull(response.body()).getError()) {
                        Toast.makeText(getApplicationContext(), "Cập nhật yêu cầu thành công!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
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
//        btn_appoiment = findViewById(R.id.btn_appoit_order);
        btn_homel = findViewById(R.id.btn_home_order);
        rec2 = findViewById(R.id.ServicerecyclerView);
        totalamt = findViewById(R.id.totalamt);
        btnInPosition = findViewById(R.id.btnInPosition1);
        btnDone = findViewById(R.id.btnDone);
        btnCancel = findViewById(R.id.btnCancel);
        llOptions = findViewById(R.id.llOption);
        txtnote = findViewById(R.id.txtNote);
        statustitle = findViewById(R.id.tv_status);
    }

    private void getintentdata(Intent intent) {
        idorder = intent.getStringExtra("id_order");
        Log.d("idorder", idorder);
    }


}
