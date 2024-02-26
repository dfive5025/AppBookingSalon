package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.Appontmentactivity;
import com.example.myapplication.Model.Content;
import com.example.myapplication.Model.Photo;
import com.example.myapplication.R;
import com.example.myapplication.Timeorderactivity;
import com.example.myapplication.adapter.Photoadapter;
import com.example.myapplication.adapter.Typeadapter;
import com.example.myapplication.notificationNewOrderActivity;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragment extends Fragment {

    private CircleIndicator3 circleIndicator3;
    private ViewPager2 viewPager2;
    private List<Photo> photoList;
    private Handler handler=new Handler(Looper.getMainLooper());
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            int current=viewPager2.getCurrentItem();
            if(current==photoList.size()-1)
            {
                viewPager2.setCurrentItem(0);
            }
            else {
                viewPager2.setCurrentItem(current+1);
            }
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        ImageView btnapoi=view.findViewById(R.id.btnappoi);
        ImageView btnNotifi=view.findViewById(R.id.btnNotifi);

        viewPager2=view.findViewById(R.id.viewpager2);

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,3000);
            }
        });
        CompositePageTransformer transformer=new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        viewPager2.setPageTransformer(transformer);
        circleIndicator3=view.findViewById(R.id.indicator);
        photoList=getListPhoto();
        Photoadapter photoadapter=new Photoadapter(photoList);
        viewPager2.setAdapter(photoadapter);
        circleIndicator3.setViewPager(viewPager2);
        btnNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), notificationNewOrderActivity.class);
                startActivity(intent);
            }
        });
        btnapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), Appontmentactivity.class);
                startActivity(intent);

            }
        });
        List<Content> contents=new ArrayList<>();
        contents.add(new Content("Gội đầu","4.8","Mang đến cho bạn cảm giác thoải mái khi gội , sạch sẽ,chất lượng","890"));
        contents.add(new Content("Cắt tóc","4.8","Mang đến cho bạn kiểu tóc ưng ý nhất bới những nhân viên chuyên nghiệp","690"));
        contents.add(new Content("Làm Nail","4.9","Chăm sóc , tạo kiểu thiết kế nail luôn là những dịch vụ hàng đầu ở đây","890"));
        contents.add(new Content("Dưỡng da","4.8","Tạo bạn làn da bóng mịn , căng tràn sức sống","890"));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        // Thay thế MyItem bằng lớp dữ liệu thực tế của bạn
       Typeadapter adapter = new Typeadapter(contents, getContext(),getLayoutInflater());
        recyclerView.setAdapter(adapter);


        return view;
    }
    private List<Photo> getListPhoto(){
        List<Photo> list=new ArrayList<>();
        list.add(new Photo(R.drawable.img1));
        list.add(new Photo(R.drawable.img2));
        list.add(new Photo(R.drawable.img3));
        list.add(new Photo(R.drawable.img4));
        return list;


    }
}