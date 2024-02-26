package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Apiresponseadd;
import com.example.myapplication.Model.Content;
import com.example.myapplication.Model.Services;
import com.example.myapplication.R;

import java.util.List;

import javax.xml.namespace.QName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Typeadapter extends RecyclerView.Adapter<Typeadapter.ViewHolder> {
    List<Content> contenlist;

    Context context;
    LayoutInflater inflater;

    public Typeadapter(List<Content> contenlist, Context context, LayoutInflater inflater) {
        this.contenlist = contenlist;

        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.typecard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Content content = contenlist.get(position);
        holder.name.setText(content.getNamservice());

        if (content.getNamservice().equals("Cắt tóc")) {
            holder.imgae.setImageResource(R.drawable.cuthair);

        } else if (content.getNamservice().equals("Gội đầu")) {
            holder.imgae.setImageResource(R.drawable.washhair);
        } else if (content.getNamservice().equals("Làm Nail")) {
            holder.imgae.setImageResource(R.drawable.nail);
        } else if (content.getNamservice().equals("Dưỡng da")) {
            holder.imgae.setImageResource(R.drawable.skin123);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialogView = inflater.inflate(R.layout.typecarditem, null);
                builder.setView(dialogView);

                // Đặt các thuộc tính khác cho AlertDialog (tiêu đề, nút OK, nút Cancel, vv.)


                // Tạo AlertDialog từ AlertDialog.Builder
                AlertDialog alertDialog = builder.create();
                // Sử dụng LayoutInflater để inflate layout XML cho nội dung của AlertDialog


                TextView textrate = dialogView.findViewById(R.id.txtrate);
                TextView textdes = dialogView.findViewById(R.id.txtdes);
                TextView textcount = dialogView.findViewById(R.id.txtuser);

                textrate.setText(content.getRate());
                textdes.setText(content.getDescription());
                textcount.setText(content.getUsercount());


                // Đặt layout XML làm nội dung cho AlertDialog


                // Hiển thị AlertDialog
                alertDialog.show();
            }
        });


        ;
    }

    @Override
    public int getItemCount() {
        if (contenlist != null) {
            return contenlist.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imgae;

        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgae = itemView.findViewById(R.id.sphoto);
            name = itemView.findViewById(R.id.nameid);
            linearLayout = itemView.findViewById(R.id.line);
        }
    }
}
