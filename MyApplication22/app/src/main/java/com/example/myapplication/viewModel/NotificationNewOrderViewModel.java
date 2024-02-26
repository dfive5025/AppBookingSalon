package com.example.myapplication.viewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.LogEventModel;
import com.example.myapplication.repository.RetrofitInstance;
import com.example.myapplication.repository.RetrofitInterface;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NotificationNewOrderViewModel extends ViewModel {
    private final MutableLiveData<List<LogEventModel>> arrayListMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<LogEventModel>> getRequestListInfo() {
        return arrayListMutableLiveData;
    }

    public void setLogEventListInfo(String iduser, Context context) {
        RetrofitInterface retrofitInterface = RetrofitInstance.getService();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", "");
        int iduser1 = sharedPreferences.getInt("iduser",0);
        RetrofitInstance.setToken(token);
        Call<List<LogEventModel>> calls = retrofitInterface.readAllLogEventByIdCustomer(iduser1);
        Log.d("iduser","là" + iduser1);
        calls.enqueue(new Callback<List<LogEventModel>>() {
            @Override
            public void onResponse(Call<List<LogEventModel>> call, retrofit2.Response<List<LogEventModel>> response) {
                List<LogEventModel> requests = new ArrayList<>();
                List<LogEventModel> list = response.body();
               // Log.d("idsl",list.get(0).getId_order());
                if (list != null) {
                    for (LogEventModel request : list) {
                        requests.add(request);
                    }
                    arrayListMutableLiveData.setValue(requests);
                } else {
                    Toast.makeText(context, "Lấy danh sách yêu cầu thất bại!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LogEventModel>> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}
