package com.babybong.appting.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.babybong.appting.BaseActivity;
import com.babybong.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.login.service.DataStoredService;
import com.babybong.appting.model.MemberDto;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoon on 2015-05-14.
 */
public class IntroductionChoiceEditActivity extends BaseActivity {
    private EditText inputIntroduction;
    private EditText inputHobby;
    private EditText inputIdealType;
    private EditText inputMyAppeal;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_choice_edit);
        inputIntroduction = (EditText) findViewById(R.id.input_introduction);
        inputHobby = (EditText) findViewById(R.id.input_hobby);
        inputIdealType = (EditText) findViewById(R.id.input_ideal);
        inputMyAppeal = (EditText) findViewById(R.id.input_myappeal);
        saveBtn = (Button) findViewById(R.id.save_btn);

        setData();
    }

    private void setData() {
        inputIntroduction.setText(DataStoredService.getStoredData(IntroductionChoiceEditActivity.this, DataStoredService.STORE_INTRODUCTION));
        inputHobby.setText(DataStoredService.getStoredData(IntroductionChoiceEditActivity.this, DataStoredService.STORE_HOBBY));
        inputIdealType.setText(DataStoredService.getStoredData(IntroductionChoiceEditActivity.this, DataStoredService.STORE_IDEALTYPE));
        inputMyAppeal.setText(DataStoredService.getStoredData(IntroductionChoiceEditActivity.this, DataStoredService.STORE_MYAPPEAL));

    }

    public void onClickSaveBtn(View view) {
        try {
            saveIntroduction();
        } catch (Exception e) {
            Toast.makeText(IntroductionChoiceEditActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void saveIntroduction() throws Exception {
        final String mail = DataStoredService.getStoredData(IntroductionChoiceEditActivity.this, DataStoredService.STORE_MAIL);
        final String introduction = inputIntroduction.getText().toString();
        final String hobby = inputHobby.getText().toString();
        final String idealType = inputIdealType.getText().toString();
        final String myAppeal = inputMyAppeal.getText().toString();

        String memberId = mail.split("@")[0];
        String url = AppController.API_URL + "/members/introduction/" + memberId; //멤버개인정보 추가 url
        Log.d("introduction", "mail : " + mail + "|url: " + url);
        MemberDto memberDto = new MemberDto();
        memberDto.setMail(mail);
        memberDto.setSelfIntroduction(introduction);
        memberDto.setHobby(hobby);
        memberDto.setIdealType(idealType);
        memberDto.setMyAppeal(myAppeal);

        ObjectMapper mapper = new ObjectMapper();
        final JSONObject jsonObject = new JSONObject(mapper.writeValueAsString(memberDto));

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {

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
                        //String phoneAuth = memberDto.getString("phoneAuth");
                        dataStore();

                    } else {
                        alertMessage("잠시후 다시 시도하세요.");
                    }
                } catch (JSONException e) {
                    alertMessage("담당자에게 문의하세요.");
                    Log.e("login", "JSONException error : " + e.toString());
                }
            }

            private void dataStore() {
                DataStoredService.storeData(IntroductionChoiceEditActivity.this, DataStoredService.STORE_INTRODUCTION, introduction);
                DataStoredService.storeData(IntroductionChoiceEditActivity.this, DataStoredService.STORE_HOBBY, hobby);
                DataStoredService.storeData(IntroductionChoiceEditActivity.this, DataStoredService.STORE_IDEALTYPE, idealType);
                DataStoredService.storeData(IntroductionChoiceEditActivity.this, DataStoredService.STORE_MYAPPEAL, myAppeal);

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
