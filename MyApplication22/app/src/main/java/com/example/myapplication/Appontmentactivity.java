package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Request;
import com.example.myapplication.Model.Services;
import com.example.myapplication.adapter.AppoitmentAdapater;
import com.example.myapplication.databinding.ActivityAppoitmentBinding;
import com.example.myapplication.viewModel.AppointmentViewModel;
import com.example.myapplication.viewModel.BookUserViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Appontmentactivity extends AppCompatActivity {
    static String key = "";
    AppoitmentAdapater appoitmentAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActivityAppoitmentBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_appoitment);
        View view = binding.getRoot();
        RecyclerView recyclerView = view.findViewById(R.id.req_recyclerview);
        AppointmentViewModel viewModel = new AppointmentViewModel(this, this);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getRequestlist().observe(this, new Observer<List<Request>>() {
            @Override
            public void onChanged(List<Request> requests) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Collections.reverse(requests);
                appoitmentAdapater = new AppoitmentAdapater(getApplicationContext(), (ArrayList<Request>) requests);
                recyclerView.setAdapter(appoitmentAdapater);
                appoitmentAdapater.notifyDataSetChanged();

                List<String> servicesArraylist = new ArrayList<>();

                if (requests.size() > 0) {
                    for (int e = 0; e < requests.size(); e++) {
                        ArrayList<Services> temp = (ArrayList<Services>) requests.get(e).getServices();
                        if (temp.size() > 0) {
                            for (int j = 0; j < temp.size(); j++) {
                                servicesArraylist.add(temp.get(j).getType());
                            }
                        }
                    }
                }


                // getWordFrequencies(servicesArraylist);  //sua


            }
        });

        SearchView searchView = view.findViewById(R.id.searchbill); //sua92
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Nhập từ khóa tìm kiếm ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                appoitmentAdapater.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                appoitmentAdapater.getFilter().filter(newText);
                return false;
            }
        });
    }
}