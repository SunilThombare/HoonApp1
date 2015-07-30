package com.babybong.appting.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.babybong.appting.BaseActivity;
import com.babybong.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.common.ApiAddress;
import com.babybong.appting.login.service.DataStoredService;
import com.babybong.appting.model.MemberDto;

/**
 * 회원가입
 */
public class SignupActivity extends BaseActivity {
    EditText inputEmail, inputPw, inputPwConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputEmail = (EditText) findViewById(R.id.input_EMAIL);
        inputPw = (EditText) findViewById(R.id.input_PW);
        inputPwConfirm = (EditText) findViewById(R.id.input_PW_confirm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 가입하기 버튼클릭
     * @param view
     */
    public void onClickSignup(View view) {
        Log.d("login", "가입하기 클릭!!");
        if (!validPassword()) {
            return;
        }
        try {
            registMember();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validPassword() {
        final String pwd = inputPw.getText().toString();
        final String pwdConfrim = inputPwConfirm.getText().toString();
        if (!pwd.equals(pwdConfrim)) {
            alertMessage("패스워드가 일치하지 않습니다. \n다시 입력하세요.");
            inputPwConfirm.setFocusable(true);
            inputPwConfirm.requestFocus();
            return false;
        }
        return true;
    }

    private void registMember() throws Exception {
        final String email = inputEmail.getText().toString();
        final String pwd = inputPw.getText().toString();

        MemberDto memberDto = new MemberDto();
        memberDto.setMail(email);
        memberDto.setPassword(pwd);
        memberDto.setCreateAt(new Date());
        memberDto.setUpdateAt(new Date());
        //memberDto.setDeviceRegId();

        ObjectMapper mapper = new ObjectMapper();
        final JSONObject jsonObject = new JSONObject(mapper.writeValueAsString(memberDto));
        //final JSONObject jsonObject = new JSONObject();

       /* try {
            jsonObject.put("memberDto", memberDto);
        } catch (JSONException e) {
            Log.e("error", e.getMessage());
        }*/
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, ApiAddress.MEMBER_CREATE, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("onResponse", "response.toString() : " + response.toString());
                        try {
                            if ((boolean)response.get("apiSuccess")) {
                                DataStoredService.storeData(SignupActivity.this, DataStoredService.STORE_MAIL, email);
                                DataStoredService.storeData(SignupActivity.this, DataStoredService.STORE_PWD, pwd);
                                nextActivity();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", "에러 : " + error.toString());
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {
                try {
                    Log.i("json", jsonObject.toString());
                    return jsonObject.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    private boolean isCorrectPwd(String inputPwd, String dbPwd) {
        if (inputPwd.equals(dbPwd)) {
            return true;
        }
        return false;
    }

    private void nextActivity() {
        Intent intent = new Intent(SignupActivity.this, PhoneAuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
