package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.LogEventModel;
import com.example.myapplication.adapter.NotificationNewOrderAdapter;
import com.example.myapplication.databinding.ActivityNotificationNewOrderBinding;
import com.example.myapplication.viewModel.NotificationNewOrderViewModel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class notificationNewOrderActivity extends AppCompatActivity {
    String User_id;
    NotificationNewOrderAdapter notificationNewOrderAdapter;
    RecyclerView recyclerView;
    TextView noservice;
    String action = "";
    ImageButton recommend;
    String loginKey;
    static String key = "";

    String namesl1;
    SearchView searchView;
    ImageView ic_notfound;

    List<LogEventModel> requests = new ArrayList<>();
    String saloonId1;
    String formShowWhere;

    ActivityNotificationNewOrderBinding notificationNewOrderBinding;
    NotificationNewOrderViewModel notificationNewOrderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationNewOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_notification_new_order);
        notificationNewOrderViewModel = new ViewModelProvider(this).get(NotificationNewOrderViewModel.class);
        notificationNewOrderBinding.setLifecycleOwner(this);
        notificationNewOrderBinding.setNotificationNewOrderViewModel(notificationNewOrderViewModel);

        action = getIntent().getAction();
        getIntentData(getIntent());


        recyclerView = notificationNewOrderBinding.reqRecyclerview;
        recommend = notificationNewOrderBinding.recommend;

        noservice = findViewById(R.id.noservice);
        ic_notfound = findViewById(R.id.notfound);
        ImageView btnback = notificationNewOrderBinding.btnbackService;
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShowAppMent(); //sua
        Collections.reverse(requests);
        notificationNewOrderAdapter = new NotificationNewOrderAdapter(getApplicationContext(), requests, loginKey, namesl1);
        recyclerView.setAdapter(notificationNewOrderAdapter);

        searchView = findViewById(R.id.searchbill);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Nhập từ khóa tìm kiếm (ID đơn)");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                notificationNewOrderAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                notificationNewOrderAdapter.getFilter().filter(newText);
                return false;
            }
        });

        notificationNewOrderViewModel.getRequestListInfo().observe(this, new Observer<List<LogEventModel>>() {
            @Override
            public void onChanged(List<LogEventModel> LogEventModel) {
                // ArrayList<String> servicesArraylist = new ArrayList<>();
                Collections.reverse(LogEventModel);
                notificationNewOrderAdapter = new NotificationNewOrderAdapter(notificationNewOrderActivity.this, LogEventModel, loginKey, namesl1);
                recyclerView.setAdapter(notificationNewOrderAdapter);

//                if (requestModels.size() > 0) {
//                    for (int e = 0; e < requestModels.size(); e++) {
//                        ArrayList<ServiceModel> temp = requestModels.get(e).getServices();
//                        if (temp.size() > 0) {
//                            for (int j = 0; j < temp.size(); j++) {
//                                servicesArraylist.add(temp.get(j).getType());
//                            }
//                        }
//                    }
//                }

//                if (servicesArraylist.size() == 0) {
//                    noservice.setVisibility(TextView.VISIBLE);
//                    ic_notfound.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(TextView.INVISIBLE);
//                    Toast.makeText(notificationNewOrderActivity.this, "No Past Orders available", Toast.LENGTH_SHORT).show();
//                } else {
//                    noservice.setVisibility(TextView.INVISIBLE);
//                    recyclerView.setVisibility(TextView.VISIBLE);
//                    getWordFrequencies(servicesArraylist);
//                }
            }
        });
    }

    public void getIntentData(Intent intent) { //sua
        saloonId1 = intent.getStringExtra("idsaloon12");
        namesl1 = intent.getStringExtra("slname1");
        formShowWhere = intent.getStringExtra("openformFromWhere");
    }

    private void ShowAppMent() {
        notificationNewOrderViewModel.setLogEventListInfo(saloonId1, this);
    }


    public static Map<String, Integer> getWordFrequencies(List<String> words) {
        Map<String, Integer> wordFrequencies = new LinkedHashMap<String, Integer>();
        if (words != null) {
            for (String word : words) {
                if (word != null) {
                    word = word.trim();
                    if (!wordFrequencies.containsKey(word)) {
                        wordFrequencies.put(word, 0);
                    }
                    int count = wordFrequencies.get(word);
                    wordFrequencies.put(word, ++count);
                }
            }
        }

        Map.Entry<String, Integer> entry = wordFrequencies.entrySet().iterator().next();
        key = entry.getKey();
        Integer value = entry.getValue();
        return wordFrequencies;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
