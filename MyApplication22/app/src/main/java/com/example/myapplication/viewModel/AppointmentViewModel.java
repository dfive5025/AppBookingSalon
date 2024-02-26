package com.example.myapplication.viewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Appontmentactivity;
import com.example.myapplication.Model.Request;
import com.example.myapplication.Model.Services;
import com.example.myapplication.Model.Staffcuthair;
import com.example.myapplication.constants.constants;
import com.example.myapplication.repository.RetrofitInstance;
import com.example.myapplication.repository.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppointmentViewModel extends ViewModel {
    public MutableLiveData<List<Request>> requestlist = new MutableLiveData<>();
    Context context;
    Appontmentactivity appontmentactivity;

    public AppointmentViewModel(Context context, Appontmentactivity appontmentactivity) {

        this.appontmentactivity = appontmentactivity;
        this.context = context;
        getlistrequest();
    }

    com.example.myapplication.constants.constants constants = new constants();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(constants.baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    com.example.myapplication.backendapi backendapi = retrofit.create(com.example.myapplication.backendapi.class);

    public void onBackpress() {

        appontmentactivity.onBackPressed();
    }

    public void getlistrequest() {
        RetrofitInterface retrofitInterface = RetrofitInstance.getService();
        SharedPreferences sharedPref = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String emailuser = sharedPref.getString("emailuser", "");
        String token = sharedPref.getString("Token", "");
        RetrofitInstance.setToken(token);
        Call<List<Request>> listCall = retrofitInterface.getlistsrequestbyemail(emailuser);
        listCall.enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                List<Request> requests = new ArrayList<>();
                List<Request> requestList = response.body();
                for (Request request : requestList) {
                    String serviceslist = request.getJsonlistservice();
                    String staffcuthairList1 = request.getJsonlist();


                    Gson gson1 = new Gson();
                    Type type = new TypeToken<ArrayList<Services>>() {
                    }.getType();
                    ArrayList<Services> servicelist = gson1.fromJson(serviceslist, type);


                    Gson gson2 = new Gson();
                    Type type1 = new TypeToken<ArrayList<Staffcuthair>>() {
                    }.getType();
                    ArrayList<Staffcuthair> staffList1 = gson2.fromJson(staffcuthairList1, type1);

                    request.setStaffcuthairList(staffList1);
                    request.setServices(servicelist);


                    requests.add(request);
                }

                requestlist.setValue(requests);


            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {

            }
        });


    }

    public MutableLiveData<List<Request>> getRequestlist() {
        return requestlist;
    }

    public void setRequestlist(MutableLiveData<List<Request>> requestlist) {
        this.requestlist = requestlist;
    }
}
