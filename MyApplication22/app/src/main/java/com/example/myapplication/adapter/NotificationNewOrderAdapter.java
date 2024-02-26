package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.BillDetailActivity1;
import com.example.myapplication.Model.LogEventModel;
import com.example.myapplication.R;
import com.example.myapplication.constants.constants;
import com.example.myapplication.repository.RetrofitInstance;
import com.example.myapplication.repository.RetrofitInterface;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class NotificationNewOrderAdapter extends RecyclerView.Adapter<NotificationNewOrderAdapter.ViewHolder> implements Filterable {
    Context context;
    List<LogEventModel> list;
    List<LogEventModel> listrequest1;
    String loginKey;
    String loginId;
    String namesl1;

    public NotificationNewOrderAdapter(Context context, List<LogEventModel> list, String loginKey, String namesl) {
        this.context = context;
        this.list = list;
        this.listrequest1 = list;
        this.loginKey = loginKey;
        this.namesl1 = namesl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_tem, null, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final LogEventModel request = list.get(position);
        if (request.getDescription().contains("Cancel")){
            holder.txttitle.setText("Lịch hẹn vừa bị hủy bỏ ");
            holder.iconstatus.setImageResource(R.drawable.icon_cancel);
        }else if (request.getDescription().contains("InPosition")){
            holder.txttitle.setText("Cập nhật: InPosition ");
            holder.iconstatus.setImageResource(R.drawable.pending);
        }else if (request.getDescription().contains("Paid")){
            holder.txttitle.setText("Cập nhật lịch hẹn: Paid ");
            holder.iconstatus.setImageResource(R.drawable.ic_success123);
        }
        holder.txt_id_order.setText("#"+request.getId_order()); //sua
        holder.txtTimeCreate.setText(request.getCreatAt());
        holder.txtCreator.setText(request.getCreator());
        if (request.getCustomerMarkAsRead().equals("0")){
            holder.btnSt.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BillDetailActivity1.class);
                intent.putExtra("id_order", request.getId_order());
                holder.btnSt.setVisibility(View.INVISIBLE);

                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        constants.URL_UpdateLogEvent,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(v.getContext(), "Lỗi", Toast.LENGTH_LONG).show();
//                            progressDialog.dismiss();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("IDlogevent", request.getId());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

                context.startActivity(intent);
            }
        });



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
                    ArrayList<LogEventModel> list1 = new ArrayList<>();
                    for (LogEventModel request : listrequest1) {
                        if (request.getId_order().toLowerCase(Locale.ROOT).contains((strSearch.toLowerCase()))) {
                            list1.add(request);
                        } else if (String.valueOf(request.getCreatAt()).contains(strSearch)) {
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
                list = (ArrayList<LogEventModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_id_order, txtCreator,txtTimeCreate, txttitle;
        CircleImageView   iconstatus;
        ImageView btnSt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id_order = itemView.findViewById(R.id.txt_id_order);
            txttitle = itemView.findViewById(R.id.txttitle);
            txtCreator = itemView.findViewById(R.id.txtCreator);
            txtTimeCreate = itemView.findViewById(R.id.txtTimeCreate);
            btnSt = itemView.findViewById(R.id.btnStatus);
            iconstatus = itemView.findViewById(R.id.profile_image_staff);
        }
    }
}
