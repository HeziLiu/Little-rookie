package com.example.he.myqq.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.he.myqq.R;

/**
 * Created by he on 2017/1/30.
 */

public class SharedPreferrenceHelper {

    //设置需要保存的主题
    public static void setTheme(Context context, int resId) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt("theme", resId);
        editor.commit();
    }


    //获取保存的主题
    public static int getTheme(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int theme = sp.getInt("theme", R.style.AppTheme);
        return theme;
    }


    //保存密码，下次直接登录
    public static void savePassword(Context context,boolean isSave){
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("save",isSave);
        editor.commit();
    }

    public static boolean getSaveStatus(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean t=sp.getBoolean("save",false);
        return t;
    }





}
