package com.babybong.appting.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.babybong.appting.ListMainActivity;

import info.androidhive.appting.R;

import com.babybong.appting.login.service.LoginService;

/**
 * 로그인
 */
public class LoginActivity extends Activity {
    EditText inputMail, inputPw;
    CheckBox autoLogIn;

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputMail = (EditText) findViewById(R.id.input_mail);
        inputPw = (EditText) findViewById(R.id.input_PW);
        autoLogIn = (CheckBox) findViewById(R.id.Auto_LogIn);

        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();

        if(setting.getBoolean("Auto_Login_enabled", false)) {
            inputMail.setText(setting.getString("MAIL", ""));
            inputPw.setText(setting.getString("PW", ""));
            autoLogIn.setChecked(true);
        }

        autoLogIn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    Log.d("login", "아이디 패스워드 저장");
                    String mail = inputMail.getText().toString();
                    String pw = inputPw.getText().toString();

                    editor.putString("MAIL", mail);
                    editor.putString("PW", pw);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();
                } else {
                    Log.d("login", "아이디 패스워드 삭제");
                    /**
                     * remove로 지우는것은 부분삭제
                     * clear로 지우는것은 전체 삭제 입니다
                     */
//					editor.remove("MAIL");
//					editor.remove("PW");
//					editor.remove("Auto_Login_enabled");
                    editor.clear();
                    editor.commit();
                }
            }
        });
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void checkMember() {
        final String mail = inputMail.getText().toString();
        final String pw = inputPw.getText().toString();
        LoginService loginService = new LoginService(LoginActivity.this);
        loginService.loginProcess(mail, pw);
    }

    /*private void checkMember() {
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
    }*/

    private boolean isCorrectPwd(String inputPwd, String dbPwd) {
        if (inputPwd.equals(dbPwd)) {
            return true;
        }
        return false;
    }

    private void nextActivity() {
        Intent intent = new Intent(LoginActivity.this, ListMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void alertMessage(String message) {
        Log.d("alertMessage", "message : " + message);
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
