package com.babybong.appting.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.babybong.appting.GcmMainActivity;
import com.babybong.appting.ListMainActivity;

import info.androidhive.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.BaseActivity;
import com.babybong.appting.login.SignupActivity;

/**
 *  프로필 수정 메인화면
 */
public class ProfileEditActivity extends BaseActivity {
    Button imageEditBtn;
    Button basicEditBtn;
    Button selfIntroductionEditBtn;
    Button selfIntroductionChoiceEditBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        imageEditBtn = (Button) findViewById(R.id.image_edit_btn);
        basicEditBtn = (Button) findViewById(R.id.basic_edit_btn);
        selfIntroductionEditBtn = (Button) findViewById(R.id.self_introduction_edit_btn);
        selfIntroductionChoiceEditBtn = (Button) findViewById(R.id.self_introduction_choice_edit_btn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClickImageEditBtn(View view) {
        nextActivity(GcmMainActivity.class);
    }

    public void onClickBasicEditBtn(View view) {
        nextActivity(ProfileBasicEditActivity.class);
    }

    public void onClickSelfIntroductionEditBtn(View view) {
        nextActivity(GcmMainActivity.class);
    }

    public void onClickSelfIntroductionChoiceEditBtn(View view) {
        nextActivity(GcmMainActivity.class);
    }

    public void nextActivity(Class zlass) {
        Intent intent = new Intent(ProfileEditActivity.this, zlass);
        startActivity(intent);
    }

}
