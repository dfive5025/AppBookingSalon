package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Services;
import com.example.myapplication.R;
import com.example.myapplication.viewModel.BookUserViewModel;

import java.util.List;

public class Servicesadapter1 extends RecyclerView.Adapter<Servicesadapter1.ViewHolder> {
    List<Services> servicesList;


    public Servicesadapter1(List<Services> servicesList) {
        this.servicesList = servicesList;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activelist_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Services services = servicesList.get(position);
        holder.servicename.setText(services.getNameservice());

        holder.servicetype.setText(services.getType());
        holder.serviceprice.setText(services.getPrice());
        holder.add.setVisibility(View.GONE);
        holder.remove.setVisibility(View.GONE);


        ;
    }

    @Override
    public int getItemCount() {
        if (servicesList != null) {
            return servicesList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView servicename, serviceprice, servicetype;
        Button add, remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.btnadd);
            remove = itemView.findViewById(R.id.btnremove);

            servicename = itemView.findViewById(R.id.txtServicename);
            serviceprice = itemView.findViewById(R.id.txtPrice);
            servicetype = itemView.findViewById(R.id.txttype);
        }
    }
}
