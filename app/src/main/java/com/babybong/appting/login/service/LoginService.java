package com.babybong.appting.login.service;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
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

import com.babybong.appting.app.AppController;
import com.babybong.appting.login.LoginActivity;
import com.babybong.appting.login.PhoneAuthActivity;
import com.babybong.appting.main.MainActivity;
import com.babybong.appting.profile.ProfileCreateActivity;

/**
 * Created by hoon on 2015-05-10.
 */
public class LoginService {
    private Activity currentActivity;
    public LoginService(Activity activity) {
        this.currentActivity = activity;
    }
    public void loginProcess(final String mail, final String pwd) {

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
                        String phone = memberDto.getString("phone");
                        String nickName = memberDto.getString("nickName");
                        if (isCorrectPwd(pwd, dbPwd)) {
                            storeData(mail, pwd);
                            if (phone == null || "null".equals(phone)) { //전화번호가 없으면 폰인증화면으로이동
                                nextActivity(PhoneAuthActivity.class);
                            } else {
                                Log.d("login", "[" + nickName + "]멤버입니다.!! 메인화면으로 이동!! ==> " + phone);
                                if (nickName != null && !"null".equals(nickName)) {
                                    loginSuccessNextActivity(MainActivity.class);
                                } else {
                                    nextActivity(ProfileCreateActivity.class);
                                }
                            }
                        } else {
                            alertMessage("패스워드가 다릅니다.");
                            loginFailNextActivity(LoginActivity.class);
                        }
                    } else {
                        alertMessage(apiMessage);
                        loginFailNextActivity(LoginActivity.class);
                    }
                } catch (JSONException e) {
                    alertMessage("담당자에게 문의하세요.");
                    loginFailNextActivity(LoginActivity.class);
                    Log.e("login", "JSONException error : " + e.toString());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                alertMessage("잠시후 다시 시도하세요.");
                loginFailNextActivity(LoginActivity.class);
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

    private void loginSuccessNextActivity(Class nextActivityClass) {
        nextActivity(nextActivityClass);
    }

    private void loginFailNextActivity(Class nextActivityClass) {
        if (currentActivity instanceof LoginActivity) {
            return;
        }
        nextActivity(nextActivityClass);
    }

    private void nextActivity(Class nextActivityClass) {
        Intent intent = new Intent(currentActivity, nextActivityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentActivity.startActivity(intent);
    }

    private void alertMessage(String message) {
        Log.d("alertMessage", "message : " + message);
        Toast toast = Toast.makeText(currentActivity, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void storeData(String mail, String pwd) {
        DataStoredService.storeData(currentActivity, DataStoredService.STORE_MAIL, mail);
        DataStoredService.storeData(currentActivity, DataStoredService.STORE_PWD, pwd);
    }
}
