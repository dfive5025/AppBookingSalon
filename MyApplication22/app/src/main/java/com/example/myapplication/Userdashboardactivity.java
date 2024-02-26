package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.ListSalonFragment;
import com.example.myapplication.Fragment.InfoCustomerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Userdashboardactivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_Order = 1;
    private static final int FRAGMENT_infor = 2;
    private int mCurrentFragment = FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation_view);

        replaceFragment(new HomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    if (mCurrentFragment != FRAGMENT_HOME) {
                        replaceFragment(new HomeFragment());
                        mCurrentFragment = FRAGMENT_HOME;
                    }
                } else if (id == R.id.navigation_order) {
                    if (mCurrentFragment != FRAGMENT_Order) {
                        replaceFragment(new ListSalonFragment());
                        mCurrentFragment = FRAGMENT_Order;

                    }


                } else if (id == R.id.navigation_infor) {
                    if (mCurrentFragment != FRAGMENT_infor) {
                        replaceFragment(new InfoCustomerFragment());
                        mCurrentFragment = FRAGMENT_infor;
                    }
                }

                item.setChecked(true);
                return true;
            }
        });


    }

    private void replaceFragment(Fragment frag) { //chuyển màn hình

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentside, frag).addToBackStack(null).commit();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Userdashboardactivity.this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Bạn có muốn thoát thật không ?");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent1 = new Intent(Userdashboardactivity.this, Loginactivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Userdashboardactivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}