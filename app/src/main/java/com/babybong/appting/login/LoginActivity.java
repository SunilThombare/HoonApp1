package com.babybong.appting.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.babybong.appting.BaseActivity;

import com.babybong.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.login.service.LoginService;

/**
 * 로그인
 */
public class LoginActivity extends BaseActivity {
    EditText inputMail, inputPw;
    //CheckBox autoLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputMail = (EditText) findViewById(R.id.input_mail);
        inputPw = (EditText) findViewById(R.id.input_PW);
        /*autoLogIn = (CheckBox) findViewById(R.id.Auto_LogIn);

        setting = getSharedPreferences("setting", Context.MODE_PRIVATE);
        editor= setting.edit();

        if(setting.getBoolean("Auto_Login_enabled", false)) {
            inputMail.setText(setting.getString(AppController.STORE_MAIL, ""));
            inputPw.setText(setting.getString(AppController.STORE_PWD, ""));
            autoLogIn.setChecked(true);
        }*/

        /*autoLogIn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    Log.d("login", "아이디 패스워드 저장");
                    String mail = inputMail.getText().toString();
                    String pw = inputPw.getText().toString();

                    editor.putString(AppController.STORE_MAIL, mail);
                    editor.putString(AppController.STORE_PWD, pw);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();
                } else {
                    Log.d("login", "아이디 패스워드 삭제");
                    *//**//**
                     * remove로 지우는것은 부분삭제
                     * clear로 지우는것은 전체 삭제 입니다
                     *//**//*
//					editor.remove("MAIL");
//					editor.remove("PW");
//					editor.remove("Auto_Login_enabled");
                    editor.clear();
                    editor.commit();
                }
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClickLogin(View view) {
        Log.d("login", "로그인 클릭!!");
        checkMember();
    }

    public void onClickSignup(View view) {
        Log.d("login", "회원가입 화면이동 클릭!!");
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void checkMember() {
        final String mail = inputMail.getText().toString();
        final String pw = inputPw.getText().toString();
        LoginService loginService = new LoginService(LoginActivity.this);
        loginService.loginProcess(mail, pw);
    }
}
