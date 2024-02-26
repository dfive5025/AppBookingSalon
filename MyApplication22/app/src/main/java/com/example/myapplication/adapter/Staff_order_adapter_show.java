
package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Staffcuthair;
import com.example.myapplication.R;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Staff_order_adapter_show extends RecyclerView.Adapter<Staff_order_adapter_show.CustomerViewholder> {
    private List<Staffcuthair> mliststaff;
    private staffchooseorder staffchooseorder;


    Context context;

    public Staff_order_adapter_show(List<Staffcuthair> mliststaff, staffchooseorder staffchooseorder, Context context) {
        this.mliststaff = mliststaff;
        this.staffchooseorder = staffchooseorder;
        this.context = context;
//        mlistuserchoose1=mlistuserchoose;
    }

    @NonNull
    @Override
    public CustomerViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staffinforform, parent, false);

        view.findViewById(R.id.btndeletestaff);
        return new CustomerViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewholder holder, @SuppressLint("RecyclerView") int position) {
        Staffcuthair staffcuthair = mliststaff.get(position);
        if (staffcuthair == null) {
            return;
        }
        if (staffcuthair.getImage() != null) {


            Glide
                    .with(context)
                    .load(staffcuthair.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.icon_loading)
                    .into(holder.staffimagechoose);


        }
        holder.name.setText(staffcuthair.getName());
        holder.mobile.setText(staffcuthair.getMobile());
        holder.address.setText(staffcuthair.getAddress());
        holder.email.setText(staffcuthair.getEmail());
        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int n = mliststaff.indexOf(staffcuthair);

                mliststaff.remove(n);
                notifyItemRemoved(position);
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

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String strSearch = charSequence.toString();
//                if (strSearch.isEmpty()) {
//                    mlistuserchoose= mlistuserchoose1;
//                } else {
//                    List<User> list = new ArrayList<>();
//                    for (User user : mlistuserchoose1) {
//                        if (user.getFirstname().toLowerCase(Locale.ROOT).contains((strSearch.toLowerCase()))) {
//                            list.add(user);
//                        }
//                    }
//                    mlistuserchoose = list;
//                }
//                FilterResults fiterresult = new FilterResults();
//                fiterresult.values = mlistuserchoose;
//
//                return fiterresult;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//               mlistuserchoose = (List<User>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public class CustomerViewholder extends RecyclerView.ViewHolder {
        TextView name, email, mobile, address;
        Button deletebutton;
        CircleImageView staffimagechoose;

        public CustomerViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtstaffname);

            email = itemView.findViewById(R.id.txtstaffemail);
            mobile = itemView.findViewById(R.id.txtstaffmobile);
            address = itemView.findViewById(R.id.txtstaffaddress);
            deletebutton = itemView.findViewById(R.id.btndeletestaff);
            deletebutton.setVisibility(View.VISIBLE);
            staffimagechoose = itemView.findViewById(R.id.staff_image_choose);


        }
    }

}
