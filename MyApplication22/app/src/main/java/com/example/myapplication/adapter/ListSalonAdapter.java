package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.BookActivityUser;
import com.example.myapplication.Model.Saloon;
import com.example.myapplication.R;

import java.util.List;

public class ListSalonAdapter extends RecyclerView.Adapter<ListSalonAdapter.LissSalonViewHolder> {
    List<Saloon> saloonList;
    Context context;


    public ListSalonAdapter(List<Saloon> saloonList, Context context) {
        this.saloonList = saloonList;
        this.context = context;
    }

    @NonNull
    @Override
    public LissSalonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salonitemorder, parent, false);
        return new LissSalonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LissSalonViewHolder holder, int position) {
        Saloon saloon = saloonList.get(position);

        Glide
                .with(context)
                .load(saloon.getImage())
                .centerCrop()
                .placeholder(R.drawable.img5)
                .into(holder.imageView);
        holder.saloonname.setText(saloon.getName());
        holder.saloonaddress.setText(saloon.getAddress());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, BookActivityUser.class);

                intent.putExtra("mysaloon", saloon);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (saloonList != null) {
            return saloonList.size();
        }
        return 0;
    }

    public class LissSalonViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView saloonname, saloonaddress;
        LinearLayout linearLayout;

        public LissSalonViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.salonimage);
            saloonname = itemView.findViewById(R.id.salonname);
            saloonaddress = itemView.findViewById(R.id.addresssalon);
            linearLayout = itemView.findViewById(R.id.liner);
        }
    }
}
