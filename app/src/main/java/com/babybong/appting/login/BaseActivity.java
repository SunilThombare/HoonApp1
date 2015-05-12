package com.babybong.appting.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hoon on 2015-05-09.
 */
public class BaseActivity extends Activity {
    SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return getSharedPreferences(SignupActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
}
