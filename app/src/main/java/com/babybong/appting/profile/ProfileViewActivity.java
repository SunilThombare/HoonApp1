package com.babybong.appting.profile;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.babybong.appting.BaseActivity;
import com.babybong.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.login.service.DataStoredService;
import com.babybong.appting.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 프로필 보기 액티비티
 * Created by hoon on 2015-05-23.
 */
public class ProfileViewActivity extends BaseActivity {
    private TextView tvBasicProfile, tvIntroduction, tvMyappeal, tvIdeal;
    private NetworkImageView ivImage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        tvBasicProfile = (TextView)findViewById(R.id.tvBasicProfile);
        tvIntroduction = (TextView)findViewById(R.id.tvIntroduction);
        tvMyappeal = (TextView)findViewById(R.id.tvMyappeal);
        tvIdeal = (TextView)findViewById(R.id.tvIdeal);

        ivImage1 = (NetworkImageView)findViewById(R.id.ivImage1);

        getProfileDataFromServer();
    }

    private void getProfileDataFromServer() {
        final String mail = DataStoredService.getStoredData(ProfileViewActivity.this, DataStoredService.STORE_MAIL);
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
                        String nickName = memberDto.getString("nickName");
                        String job = memberDto.getString("job");
                        String religion = memberDto.getString("religion");
                        String bloodType = memberDto.getString("bloodType");
                        String address1 = memberDto.getString("address1");
                        String selfIntroduction = memberDto.getString("selfIntroduction");
                        String myAppeal = memberDto.getString("myAppeal");
                        String idealType = memberDto.getString("idealType");

                        tvBasicProfile.setText(nickName + "/" + address1 + "/" + job + "/" + religion + "/" + bloodType );
                        tvIntroduction.setText(selfIntroduction);
                        tvMyappeal.setText(myAppeal);
                        tvIdeal.setText(idealType);
                    } else {
                        alertMessage("잠시후 다시 시도하세요.");
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
}
