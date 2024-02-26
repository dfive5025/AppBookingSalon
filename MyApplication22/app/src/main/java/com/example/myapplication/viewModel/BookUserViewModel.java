package com.example.myapplication.viewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.BookActivityUser;
import com.example.myapplication.Model.Saloon;
import com.example.myapplication.Model.Services;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.adapter.Servicesadapter;
import com.example.myapplication.constants.constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookUserViewModel extends ViewModel {
    View view;
    public MutableLiveData<String> workinghr = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> mobile = new MutableLiveData<>();
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<Integer> totalmoney = new MutableLiveData<>();
    public MutableLiveData<String> totalmoneyString = new MutableLiveData<>();
    public MutableLiveData<Integer> idadmin = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateToNextScreen = new MutableLiveData<>();
    public MutableLiveData<Saloon> saloon = new MutableLiveData<>();
    public MutableLiveData<List<Services>> services = new MutableLiveData<>();
    public List<Services> servicesList = new ArrayList<>();
    public MutableLiveData<Integer> busy;

    Context context;
    BookActivityUser bookActivityUser;

    public BookUserViewModel(Saloon saloon, Context context, View view, BookActivityUser bookActivityUser) {
        this.saloon.setValue(saloon);
        this.context = context;
        this.view = view;
        this.bookActivityUser = bookActivityUser;
        totalmoney.setValue(0);
        totalmoneyString.setValue("0");
        getinforuser();
        setinforsaloon();
        getlistservice();

    }


    public void navigateToNextScreen() {
        navigateToNextScreen.setValue(true);
    }

    // Getter để lắng nghe sự kiện chuyển màn hình
    public LiveData<Boolean> getNavigateToNextScreen() {
        return navigateToNextScreen;
    }


    public MutableLiveData<Integer> getBusy() {

        if (busy == null) {
            busy = new MutableLiveData<>();
            busy.setValue(8);
        }

        return busy;
    }

    public void addtotalmoney() {
        int total = 0;
        for (Services services1 : servicesList) {
            total = total + Integer.parseInt(services1.getPrice());
        }
        if (total != 0) {
            busy.setValue(0);
        } else {
            busy.setValue(8);
        }

        totalmoneyString.setValue(String.valueOf(total));
    }

    public void addsericechoose(Services services) {

        servicesList.add(services);
        addtotalmoney();

    }

    public void removesericechoose(Services services) {
        servicesList.remove(services);
        addtotalmoney();

    }

    com.example.myapplication.constants.constants constants = new constants();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(constants.baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    com.example.myapplication.backendapi backendapi = retrofit.create(com.example.myapplication.backendapi.class);

    public void setinforsaloon() {
        if (saloon.getValue().getWorkingHr().equals("null")) {
            workinghr.setValue("Chua cai dat");
        } else {
            workinghr.setValue(saloon.getValue().getWorkingHr());
        }
    }

    public void getinforuser() {
        SharedPreferences sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int iduser = sharedPref.getInt("iduser", 0);


        Call<List<User>> call = backendapi.getinforuser(iduser);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                User user = response.body().get(0);
                email.setValue(user.getEmail());

                name.setValue(user.getName());
                mobile.setValue(user.getMobile());
                address.setValue(user.getAddress());
                idadmin.setValue(user.getIdadmin());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

    public void ggoback() {
        bookActivityUser.onBackPressed();
    }

    public void getlistservice() {

        Call<List<Services>> getlistser = backendapi.getallservices(saloon.getValue().getId_saloon());
        getlistser.enqueue(new Callback<List<Services>>() {
            @Override
            public void onResponse(Call<List<Services>> call, Response<List<Services>> response) {

                List<Services> services1 = response.body();
                services.setValue(services1);

                RecyclerView recyclerView = view.findViewById(R.id.ServicerecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

                Servicesadapter servicesadapter = new Servicesadapter(services1, BookUserViewModel.this);
                recyclerView.setAdapter(servicesadapter);


            }

            @Override
            public void onFailure(Call<List<Services>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
