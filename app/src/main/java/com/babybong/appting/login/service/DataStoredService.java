package com.babybong.appting.login.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.babybong.appting.app.AppController;

/**
 * Created by Coupang on 2015-05-13.
 */
public class DataStoredService {

    public static final String STORE_MAIL = "store_mail";
    public static final String STORE_PWD = "store_pwd";

    public static SharedPreferences getGCMPreferences(Context context) {
        return context.getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

    public static void storeData(Context context, String key, String value) {
        final SharedPreferences prefs = getGCMPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStoredData(Context context, String key) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String mail = prefs.getString(key, "");
        return mail;
    }

    public static void loginInfoClear(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        SharedPreferences.Editor editor= prefs.edit();
        editor.remove(STORE_MAIL);
        editor.remove(STORE_PWD);
        editor.commit();
    }
}
