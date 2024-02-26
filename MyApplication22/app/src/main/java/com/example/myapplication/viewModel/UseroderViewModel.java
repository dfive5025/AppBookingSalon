package com.example.myapplication.viewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.Saloon;
import com.example.myapplication.Model.User;
import com.example.myapplication.constants.constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UseroderViewModel extends ViewModel {
    public MutableLiveData<List<Saloon>> listsalon = new MutableLiveData<>();
    public MutableLiveData<Integer> idadmin = new MutableLiveData<>();
    Context context;

    public UseroderViewModel(Context context) {
        this.context = context;
        getListSalon();
    }

    com.example.myapplication.constants.constants constants = new constants();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(constants.baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    com.example.myapplication.backendapi backendapi = retrofit.create(com.example.myapplication.backendapi.class);

    public void getListSalon() {
        SharedPreferences sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int iduser = sharedPref.getInt("iduser", 0);


        Call<List<User>> call = backendapi.getinforuser(iduser);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                User user = response.body().get(0);
                idadmin.setValue(user.getIdadmin());


                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putInt("idadmin", idadmin.getValue());
                Call<List<Saloon>> call2 = backendapi.getsalonbyid(idadmin.getValue());
                call2.enqueue(new Callback<List<Saloon>>() {
                    @Override
                    public void onResponse(Call<List<Saloon>> call, Response<List<Saloon>> response) {
                        listsalon.setValue(response.body());

                    }

                    @Override
                    public void onFailure(Call<List<Saloon>> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
