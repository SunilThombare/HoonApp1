package com.babybong.appting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.babybong.appting.login.SignupActivity;

/**
 * Created by hoon on 2015-05-09.
 */
public class BaseActivity extends Activity {
    public SharedPreferences setting;
    public SharedPreferences.Editor editor;

    public SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return getSharedPreferences(SignupActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    public void alertMessage(String message) {
        Log.d("alertMessage", "message : " + message);
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
