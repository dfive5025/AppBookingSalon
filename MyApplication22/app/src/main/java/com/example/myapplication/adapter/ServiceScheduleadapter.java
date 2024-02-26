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
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Model.Services;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceScheduleadapter extends RecyclerView.Adapter<ServiceScheduleadapter.ViewHolder>{
    List<Services> list=new ArrayList<Services>();
    Context context;
    public ServiceScheduleadapter(Context context, List<Services> list){
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_mini_item,null,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Services services = list.get(position);


        holder.txtedit1.setText("Service: ");
        holder.txtedit2.setText("Type: ");
        holder.txt1.setText(services.getNameservice());
        holder.txt2.setText(services.getType());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView txtedit1,txtedit2,txt1,txt2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
         txtedit1=itemView.findViewById(R.id.txtedit1);
            txtedit2=itemView.findViewById(R.id.txtedit2);
            txt1=itemView.findViewById(R.id.txt1);
            txt2=itemView.findViewById(R.id.txt2);


        }
    }
}
