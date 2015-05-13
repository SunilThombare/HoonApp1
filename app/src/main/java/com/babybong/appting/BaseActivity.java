package com.babybong.appting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.babybong.appting.app.AppController;
import com.babybong.appting.login.SignupActivity;

/**
 * Created by hoon on 2015-05-09.
 */
public class BaseActivity extends Activity {
    public void alertMessage(String message) {
        Log.d("alertMessage", "message : " + message);
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
