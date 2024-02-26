package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.text.DecimalFormat;

public class AppUtils {

    public static void setValue(Context context,String key, boolean value){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static void setStringValue(Context context,String key, String value){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static boolean getValue(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        return  pref.getBoolean(key, false);
    }

    public static String convertMoney(int money){
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String m = decimalFormat.format(money);
        return m;
    }
}
