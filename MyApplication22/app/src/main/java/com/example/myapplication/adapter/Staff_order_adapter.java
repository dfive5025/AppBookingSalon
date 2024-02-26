
package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Model.Staffcuthair;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Staff_order_adapter extends RecyclerView.Adapter<Staff_order_adapter.CustomerViewholder> implements Filterable {
    private List<Staffcuthair> mliststaff;
    private List<Staffcuthair> mliststaff01;
    private staffchooseorder staffchooseorder;

    public Staff_order_adapter(List<Staffcuthair> mliststaff , staffchooseorder staffchooseorder) {
        this.mliststaff = mliststaff ;
        this.staffchooseorder = staffchooseorder;
        mliststaff01=mliststaff;
    }
    @NonNull
    @Override
    public CustomerViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staffinforform, parent, false);


        return new CustomerViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewholder holder, int position) {
        Staffcuthair staffcuthair = mliststaff.get(position);
        if (staffcuthair == null) {
            return;
        }
        if (!staffcuthair.getImage().equals("")) {
            Picasso.get()
                    .load(staffcuthair.getImage())
                    .placeholder(R.drawable.icon_loading)
                    .error(R.drawable.s1)
                    .into(holder.staffimagechoose);
        }
        holder.name.setText(staffcuthair.getName());
        holder.mobile.setText(staffcuthair.getMobile());
        holder.address.setText(staffcuthair.getAddress());
        holder.email.setText(staffcuthair.getEmail());
        holder.choosebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                staffchooseorder.staffchoose(staffcuthair);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mliststaff != null) {
            return mliststaff.size();
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
                        mliststaff= mliststaff01;
                    } else {
                        List<Staffcuthair> list = new ArrayList<>();
                        for (Staffcuthair staff : mliststaff01) {
                            if (staff.getName().toLowerCase(Locale.ROOT).contains((strSearch.toLowerCase()))) {
                                list.add(staff);
                            }
                        }
                        mliststaff = list;
                    }
                    FilterResults fiterresult = new FilterResults();
                    fiterresult.values = mliststaff;

                    return fiterresult;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mliststaff = (List<Staffcuthair>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
    }


    public class CustomerViewholder extends RecyclerView.ViewHolder {
        TextView name, email, mobile, address;
        Button choosebutton;
        CircleImageView staffimagechoose;

        public CustomerViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtstaffname);
            email = itemView.findViewById(R.id.txtstaffemail);
            mobile = itemView.findViewById(R.id.txtstaffmobile);
            address = itemView.findViewById(R.id.txtstaffaddress);
            choosebutton = itemView.findViewById(R.id.btnchoosestaff);
            choosebutton.setVisibility(View.VISIBLE);
            staffimagechoose = itemView.findViewById(R.id.staff_image_choose);


        }
    }
}
