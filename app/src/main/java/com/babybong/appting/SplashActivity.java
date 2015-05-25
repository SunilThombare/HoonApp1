package com.babybong.appting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import com.babybong.appting.app.AppController;
import com.babybong.appting.login.LoginActivity;
import com.babybong.appting.login.service.DataStoredService;
import com.babybong.appting.login.service.LoginService;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by hoon on 2015-05-10.
 */
public class SplashActivity extends BaseActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        onCreateTracker();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                String mail = DataStoredService.getStoredData(SplashActivity.this, DataStoredService.STORE_MAIL);
                String pw = DataStoredService.getStoredData(SplashActivity.this, DataStoredService.STORE_PWD);
                Log.d("SplashActivity", "mail : " + mail);
                Log.d("SplashActivity", "pw : " + pw);
                if (mail == null || mail.equals("")) {
                    startLoginActivity();
                } else {
                    LoginService loginService = new LoginService(SplashActivity.this);
                    loginService.loginProcess(mail, pw);
                }
            }

            private void startLoginActivity() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        handler.sendEmptyMessageDelayed(0, SPLASH_TIME_OUT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    private void onCreateTracker() {
        Tracker tracker = ((AppController)getApplication()).getTracker();
        // Builder parameters can overwrite the screen name set on the tracker.
        tracker.setScreenName("SplashActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }
}
