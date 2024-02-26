package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Model.Request;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class WorkScheduleadapter extends RecyclerView.Adapter<WorkScheduleadapter.ViewHolder>{
    List<Request> list=new ArrayList<Request>();
    Context context;
    Staffscheduleadapter staffscheduleadapter;
    ServiceScheduleadapter serviceScheduleadapter;
    public WorkScheduleadapter(Context context, ArrayList<Request> list){
        this.context = context;
        this.list = list;


    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheduleitem,null,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Request request = list.get(position);


        holder.timebegin.setText(request.getTime_begin());
        holder.timefinish.setText(request.getTime_finish());
        holder.useremail.setText(request.getEmail());
        holder.username.setText(request.getName());
        holder.uamount.setText(request.getAmount() +"đ");
        holder.ustatus.setText(request.getStatus());
        serviceScheduleadapter= new ServiceScheduleadapter(context, request.getServices());

        holder.servicerec.setAdapter(serviceScheduleadapter);
        holder.servicerec.setLayoutManager(new LinearLayoutManager(context));
        serviceScheduleadapter.notifyDataSetChanged();

        staffscheduleadapter= new Staffscheduleadapter(context, request.getStaffcuthairList());

        holder.staffrec.setAdapter(staffscheduleadapter);
        holder.staffrec.setLayoutManager(new LinearLayoutManager(context));
        staffscheduleadapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView timebegin,timefinish,username,useremail,uamount,ustatus;
       RecyclerView servicerec, staffrec;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
         timefinish=itemView.findViewById(R.id.time_finish_schedule);
         timebegin=itemView.findViewById(R.id.time_begin_schedule);
         username=itemView.findViewById(R.id.user_name_schedule);
         useremail=itemView.findViewById(R.id.user_email_schedule);
         uamount = itemView.findViewById(R.id.user_amount_schedule);
         ustatus = itemView.findViewById(R.id.user_status_schedule);
         servicerec=itemView.findViewById(R.id.list_service_schedule);
         staffrec=itemView.findViewById(R.id.list_staff_schedule);

        }
    }
}
