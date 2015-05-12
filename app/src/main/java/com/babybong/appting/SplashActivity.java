package com.babybong.appting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import info.androidhive.appting.R;

import com.babybong.appting.login.LoginActivity;
import com.babybong.appting.login.service.LoginService;

/**
 * Created by hoon on 2015-05-10.
 */
public class SplashActivity extends BaseActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                setting = getSharedPreferences("setting", 0);
                editor= setting.edit();

                if (setting.getBoolean("Auto_Login_enabled", false)) {
                    String mail = setting.getString("MAIL", "");
                    String pw = setting.getString("PW", "");
                    Log.d("SplashActivity", "mail : " + mail);
                    Log.d("SplashActivity", "pw : " + pw);
                    if (mail == null || mail.equals("")) {
                        startLoginActivity();
                    } else {
                        LoginService loginService = new LoginService(SplashActivity.this);
                        loginService.loginProcess(mail, pw);
                    }
                } else {
                    startLoginActivity();
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
}
