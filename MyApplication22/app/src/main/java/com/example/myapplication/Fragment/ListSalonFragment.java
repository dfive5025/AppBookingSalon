package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Model.Photo;
import com.example.myapplication.Model.Saloon;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ListSalonAdapter;
import com.example.myapplication.adapter.Photoadapter;
import com.example.myapplication.databinding.OrderframentBinding;
import com.example.myapplication.viewModel.UseroderViewModel;
import com.example.myapplication.viewModel.UserprofileViewModel;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class ListSalonFragment extends Fragment {
    OrderframentBinding binding;

    public ListSalonFragment() {

    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.img1));
        list.add(new Photo(R.drawable.img2));
        list.add(new Photo(R.drawable.img3));
        list.add(new Photo(R.drawable.img4));
        return list;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.orderframent, container, false);

        View view = binding.getRoot();
        UseroderViewModel useroderViewModel = new UseroderViewModel(getContext());

        binding.setUserorderview(useroderViewModel);
        binding.setLifecycleOwner(this);


        RecyclerView recyclerView = view.findViewById(R.id.listsalon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (useroderViewModel.listsalon.getValue() != null) {
            ListSalonAdapter listSalonAdapter = new ListSalonAdapter(useroderViewModel.listsalon.getValue(), getContext());
            recyclerView.setAdapter(listSalonAdapter);

        }
        useroderViewModel.listsalon.observe(getViewLifecycleOwner(), new Observer<List<Saloon>>() {
            @Override
            public void onChanged(List<Saloon> saloons) {
                ListSalonAdapter listSalonAdapter = new ListSalonAdapter(useroderViewModel.listsalon.getValue(), getContext());
                recyclerView.setAdapter(listSalonAdapter);

            }
        });


        return view;

    }
}