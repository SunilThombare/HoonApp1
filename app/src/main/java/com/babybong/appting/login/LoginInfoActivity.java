package com.babybong.appting.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.babybong.appting.BaseActivity;
import com.babybong.appting.ListMainActivity;

import com.babybong.appting.R;
import com.babybong.appting.app.AppController;

/**
 * 로그인 정보
 */
public class LoginInfoActivity extends BaseActivity {
    EditText inputMail, inputPw;
    CheckBox autoLogIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_info);

        inputMail = (EditText) findViewById(R.id.input_mail);
        inputPw = (EditText) findViewById(R.id.input_PW);
        autoLogIn = (CheckBox) findViewById(R.id.Auto_LogIn);

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
        Intent intent = new Intent(LoginInfoActivity.this, SignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void checkMember() {
        final String mail = inputMail.getText().toString();
        final String pwd = inputPw.getText().toString();
        String url = AppController.API_URL + "/members/findMember";

        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mail", mail);
        } catch (JSONException e) {
            Log.e("error", e.getMessage());
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    boolean isApiSuccess = response.getBoolean("apiSuccess");
                    String apiMessage = response.getString("apiMessage");
                    Log.d("isMember", "isApiSuccess : " + isApiSuccess);
                    Log.d("isMember", "apiMessage : " + apiMessage);
                    if (isApiSuccess) {
                        JSONObject memberDto = response.getJSONObject("dto");
                        String dbPwd = memberDto.getString("password");
                        if (isCorrectPwd(pwd, dbPwd)) {
                            Log.d("login", "멤버입니다.!! 메인화면으로 이동!!");
                            nextActivity();
                        } else {
                            alertMessage("패스워드가 다릅니다.");
                        }
                    } else {
                        alertMessage(apiMessage);
                    }
                } catch (JSONException e) {
                    alertMessage("담당자에게 문의하세요.");
                    Log.e("login", "JSONException error : " + e.toString());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                alertMessage("잠시후 다시 시도하세요.");
                Log.e("login", "VolleyError error : " + error.toString());
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
        Intent intent = new Intent(LoginInfoActivity.this, ListMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
