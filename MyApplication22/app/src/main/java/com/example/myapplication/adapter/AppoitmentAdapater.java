package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Model.Request;
import com.example.myapplication.Model.Saloon;
import com.example.myapplication.R;
import com.example.myapplication.constants.constants;
import com.example.myapplication.repository.RetrofitInstance;
import com.example.myapplication.repository.RetrofitInterface;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppoitmentAdapater extends RecyclerView.Adapter<AppoitmentAdapater.ViewHolder> implements Filterable {
    Context context;
    ArrayList<Request> list = new ArrayList<>();
    ArrayList<Request> listrequest1 = new ArrayList<>();

    public AppoitmentAdapater(Context context, ArrayList<Request> list) {
        this.context = context;
        this.list = list;
        this.listrequest1 = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appo_card, null, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Request request = list.get(position);
//        String un = getUsername(request.getId_order());
        holder.txtuser.setText(request.getName()); //sua
        holder.txtamount.setText(String.valueOf(request.getAmount()) + "đ");
        holder.txtTime.setText(request.getTime_begin() + " đến " + request.getTime_finish());
        holder.status.setText(request.getStatus());

        holder.txtmobile.setText(request.getMobile());
        holder.txtrectime.setText(request.getNote());
        holder.txtDate.setText(request.getDate());
        getnamesalon(Integer.parseInt(request.getId_saloon()), holder);

//        holder.paystatus.setText(request.get);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        holder.rvServices.setLayoutManager(new LinearLayoutManager(context));

        Servicesadapter1 servicesadapter1 = new Servicesadapter1(request.getServices());
        holder.rvServices.setAdapter(servicesadapter1);
        servicesadapter1.notifyDataSetChanged();


        if (request.getStatus().equals("Cancel") || request.getStatus().equals("Paid")
                || request.getStatus().equals("InPosition")) {
            holder.btncancel.setVisibility(View.GONE);
        }
        if (request.getStatus().equals("Pending")) {
            holder.btncancel.setVisibility(View.VISIBLE);
        }

        holder.btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                request.setStatus("Cancel");


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
                                request.setNote(rect.getText().toString());
                                holder.status.setText("Cancel");
                                holder.txtrectime.setText(rect.getText().toString());
                                setStatuss(request);
                                holder.btncancel.setVisibility(View.GONE);
                                //Toast.makeText(context.getApplicationContext(), "Entered: "+rect.getText().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

            }
        });
    }


    public void setStatuss(Request requ) {


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
                constants.URL_updaterequest,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {

                                Toast.makeText(context, "thanh cong", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
//                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("idrequest", requ.getId_order());
                params.put("status", requ.getStatus());
                params.put("note", requ.getNote());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    list = listrequest1;
                } else {
                    ArrayList<Request> list1 = new ArrayList<>();
                    for (Request request : listrequest1) {
                        if (request.getDate().toLowerCase(Locale.ROOT).contains((strSearch.toLowerCase()))) {
                            list1.add(request);
                        } else if (String.valueOf(request.getServices().get(0).getNameservice()).contains(strSearch)) {
                            list1.add(request);
                        }
                    }
                    list = list1;
                }
                FilterResults fiterresult = new FilterResults();
                fiterresult.values = list;

                return fiterresult;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<Request>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtuser, txtamount, txtDate, txtTime, status, paystatus, txtrectime, txtsaloonname, txtmobile;
        RecyclerView rvServices;
        Button btnInPosition, btnDone, btnCancel;
        ImageButton btnDeleteBill;
        LinearLayout llOptions;
        Button btncancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtuser = itemView.findViewById(R.id.user);
            txtamount = itemView.findViewById(R.id.amount);
            txtTime = itemView.findViewById(R.id.time);
            txtDate = itemView.findViewById(R.id.txtDate);
            paystatus = itemView.findViewById(R.id.paystatus);
            rvServices = itemView.findViewById(R.id.rvServices);
            status = itemView.findViewById(R.id.status);
            txtrectime = itemView.findViewById(R.id.recomendtime);
            txtmobile = itemView.findViewById(R.id.mobile);
            txtsaloonname = itemView.findViewById(R.id.txtsaloonname);
            btncancel = itemView.findViewById(R.id.btncancel);


            llOptions = itemView.findViewById(R.id.llOption);

        }
    }

    public void getnamesalon(int idsalon, ViewHolder holder) {

        if (holder.txtsaloonname.getText().equals("")) {
            RetrofitInterface retrofitInterface = RetrofitInstance.getService();
            SharedPreferences sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            String token = sharedPref.getString("Token", "");
            RetrofitInstance.setToken(token);
            Call<List<Saloon>> call2 = retrofitInterface.getsalonbyidsalon(idsalon);
//            constants constants = new constants();
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(constants.baseurl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            com.example.myapplication.backendapi backendapi = retrofit.create(com.example.myapplication.backendapi.class);
//            Call<List<Saloon>> call2 = backendapi.getsalonbyidsalon(idsalon);
            call2.enqueue(new Callback<List<Saloon>>() {
                @Override
                public void onResponse(Call<List<Saloon>> call, Response<List<Saloon>> response) {
                    holder.txtsaloonname.setText(response.body().get(0).getName());

                    notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<List<Saloon>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
