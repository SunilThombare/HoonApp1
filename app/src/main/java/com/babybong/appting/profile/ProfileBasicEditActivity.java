package com.babybong.appting.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.babybong.appting.BaseActivity;
import com.babybong.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.login.service.DataStoredService;
import com.babybong.appting.main.MainActivity;
import com.babybong.appting.model.MemberDto;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoon on 2015-05-14.
 */
public class ProfileBasicEditActivity extends BaseActivity {
    private EditText inputNickNmae;
    private EditText inputJob;
    private Button area1Btn;
    private Button bloodTypeBtn;
    private Button religionBtn;
    private Button saveBtn;

    private int areaSelectedIndex = 0;
    private int bloodTypeSelectedIndex = 0;
    private int religionSelectedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_basic_edit);
        area1Btn = (Button) findViewById(R.id.area1_btn);
        bloodTypeBtn = (Button) findViewById(R.id.bloodtype_btn);
        religionBtn = (Button) findViewById(R.id.religion_btn);
        inputNickNmae = (EditText) findViewById(R.id.input_nickname);
        inputJob = (EditText) findViewById(R.id.input_job);
        saveBtn = (Button) findViewById(R.id.save_btn);

        setData();
    }

    private void setData() {
        area1Btn.setText(DataStoredService.getStoredData(ProfileBasicEditActivity.this, DataStoredService.STORE_AREA1));
        bloodTypeBtn.setText(DataStoredService.getStoredData(ProfileBasicEditActivity.this, DataStoredService.STORE_BLOODTYPE));
        religionBtn.setText(DataStoredService.getStoredData(ProfileBasicEditActivity.this, DataStoredService.STORE_RELIGION));
        inputNickNmae.setText(DataStoredService.getStoredData(ProfileBasicEditActivity.this, DataStoredService.STORE_NICKNAME));
        inputJob.setText(DataStoredService.getStoredData(ProfileBasicEditActivity.this, DataStoredService.STORE_JOB));
    }

    public void onClickBloodTypeBtn(View view) {
        final String items[] = getResources().getStringArray(R.array.bloodType);
        dialogSelectOption("혈액형", items, 2, bloodTypeBtn, bloodTypeSelectedIndex);
        //dialogBloodTypeSelectOption();
    }

    public void onClickArea1Btn(View view) {
        final String items[] = getResources().getStringArray(R.array.area1);
        dialogSelectOption("지역", items, 1, area1Btn, areaSelectedIndex);
    }

    public void onClickReligionBtn(View view) {
        final String items[] = getResources().getStringArray(R.array.religion);
        dialogSelectOption("종교", items, 3, religionBtn, religionSelectedIndex);
    }

    private void dialogSelectOption(String title, final String[] items, final int type, final Button btn, int selectedIndex) {
        //final String items[] = getResources().getStringArray(R.array.area1);

        AlertDialog.Builder ab = new AlertDialog.Builder(ProfileBasicEditActivity.this);
        ab.setTitle(title);
        ab.setSingleChoiceItems(items, selectedIndex,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (type == 1) {
                            areaSelectedIndex = whichButton;
                        } else if (type == 2) {
                            bloodTypeSelectedIndex = whichButton;
                        } else if (type ==3) {
                            religionSelectedIndex = whichButton;
                        }
                        Log.d("log", "whichButton : " + whichButton);
                    }
                }).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (type == 1) {
                            btn.setText(items[areaSelectedIndex]);
                        } else if (type == 2) {
                            btn.setText(items[bloodTypeSelectedIndex]);
                        } else if (type ==3) {
                            btn.setText(items[religionSelectedIndex]);
                        }
                        Log.d("log", "ok whichButton : " + whichButton);
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d("log", "cancel whichButton : " + whichButton);
                    }
                });
        ab.show();
    }

    public void onClickSaveBtn(View view) {
        try {
            saveBasicProfile();
        } catch (Exception e) {
            Toast.makeText(ProfileBasicEditActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void saveBasicProfile() throws Exception {
        final String mail = DataStoredService.getStoredData(ProfileBasicEditActivity.this, DataStoredService.STORE_MAIL);
        final String nickName = inputNickNmae.getText().toString();
        final String job = inputJob.getText().toString();
        final String area1 = area1Btn.getText().toString();
        final String bloodType = bloodTypeBtn.getText().toString();
        final String religion = religionBtn.getText().toString();

        String memberId = mail.split("@")[0];
        String url = AppController.API_URL + "/members/basicProfile/" + memberId; //멤버개인정보 추가 url
        Log.d("phoneAuth", "mail : " + mail);
        MemberDto memberDto = new MemberDto();
        memberDto.setMail(mail);
        memberDto.setNickName(nickName);
        memberDto.setJob(job);
        memberDto.setAddress1(area1);
        memberDto.setBloodType(bloodType);
        memberDto.setReligion(religion);

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
                        String phoneAuth = memberDto.getString("phoneAuth");
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
                DataStoredService.storeData(ProfileBasicEditActivity.this, DataStoredService.STORE_NICKNAME, nickName);
                DataStoredService.storeData(ProfileBasicEditActivity.this, DataStoredService.STORE_AREA1, area1);
                DataStoredService.storeData(ProfileBasicEditActivity.this, DataStoredService.STORE_BLOODTYPE, bloodType);
                DataStoredService.storeData(ProfileBasicEditActivity.this, DataStoredService.STORE_RELIGION, religion);
                DataStoredService.storeData(ProfileBasicEditActivity.this, DataStoredService.STORE_JOB, job);
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
