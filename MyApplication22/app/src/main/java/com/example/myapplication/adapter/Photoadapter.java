package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Photo;
import com.example.myapplication.R;

import java.util.List;

public class Photoadapter extends RecyclerView.Adapter<Photoadapter.Photoviewholder> {

    private List<Photo> photoList;

    public Photoadapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public Photoviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemphoto, parent, false);
        return new Photoviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Photoviewholder holder, int position) {
        Photo photo = photoList.get(position);
        if (photo == null) {

            return;
        }
        holder.imgphoto.setImageResource(photo.getResourceid());
    }

    @Override
    public int getItemCount() {
        if (photoList != null) {
            return photoList.size();
        }
        return 0;
    }

    public static class Photoviewholder extends RecyclerView.ViewHolder {
        private final ImageView imgphoto;

        public Photoviewholder(@NonNull View itemView) {
            super(itemView);
            imgphoto = itemView.findViewById(R.id.photoimg);
        }
    }


}
