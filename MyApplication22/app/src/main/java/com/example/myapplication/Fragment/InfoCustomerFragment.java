package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.example.myapplication.R;
import com.example.myapplication.viewModel.UserprofileViewModel;
import com.example.myapplication.databinding.UserinforfragmentBinding;

import java.util.ArrayList;
import java.util.List;


public class InfoCustomerFragment extends Fragment {


    UserinforfragmentBinding binding;
    public InfoCustomerFragment() {


    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(
                inflater, R.layout.userinforfragment, container, false);

        View view = binding.getRoot();
        UserprofileViewModel userprofileViewModel=new UserprofileViewModel(getContext(),getLayoutInflater());
        binding.setUserprofile(userprofileViewModel);
        binding.setLifecycleOwner(this);
        Spinner spinner=view.findViewById(R.id.spinner1);
       userprofileViewModel.getnameadmin(spinner);

        return view;
    }
}