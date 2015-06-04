package com.babybong.appting.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.babybong.appting.login.service.DataStoredService;
import com.babybong.appting.model.MemberDto;

/**
 * 폰인증
 */
public class PhoneAuthActivity extends BaseActivity  implements NumberPicker.OnValueChangeListener {
    private EditText inputNmae;
    private EditText inputBirthday;
    private RadioGroup radioGroupSex;
    private EditText inputPhoneNumber1, inputPhoneNumber2, inputPhoneNumber3;
    private Button phoneAuthBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneauth);
        inputNmae = (EditText) findViewById(R.id.input_name);
        inputBirthday = (EditText) findViewById(R.id.input_birthday);
        inputBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mMainDialog = createDialog();
                    mMainDialog.show();
                }
            }
        });

        radioGroupSex = (RadioGroup) findViewById(R.id.radiogroup_sex);
        inputPhoneNumber1 = (EditText) findViewById(R.id.input_phone_number1);
        inputPhoneNumber2 = (EditText) findViewById(R.id.input_phone_number2);
        inputPhoneNumber3 = (EditText) findViewById(R.id.input_phone_number3);

        phoneAuthBtn = (Button) findViewById(R.id.phone_auth_btn);

        editTextListener(inputPhoneNumber1, inputPhoneNumber2, 3);
        editTextListener(inputPhoneNumber2, inputPhoneNumber3, 4);
        editTextListener(inputPhoneNumber3, phoneAuthBtn, 4);

    }

    public static String getPhoneNumber(Activity act) {
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager) act.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    private void editTextListener(final EditText targetEditText, final TextView nextEditText, final int maxLength) {
        targetEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (targetEditText.getText().toString().length() == maxLength) {     //size as per your requirement
                    nextEditText.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 폰인증 버튼클릭
     * @param view
     */
    public void onClickPhoneAuth(View view) {
        Log.d("login", "폰인증버튼 클릭!!");

        try {
            registMemberPrivateInfo();


        } catch (Exception e) {
            Toast.makeText(PhoneAuthActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private String getPhoneNumber() {
        return inputPhoneNumber1.getText().toString() + inputPhoneNumber2.getText().toString() + inputPhoneNumber3.getText().toString();
    }

    /**
     * 6자리 인증코드 생성메소드
     * @return
     */
    private String getAuthNumber() {
        double authNumber = Math.floor(Math.random() * 1000000) + 100000;
        return String.valueOf((int)authNumber);
    }

    private void registMemberPrivateInfo() throws Exception {
        final String name = inputNmae.getText().toString();
        final String birthday = inputBirthday.getText().toString();

        int checkedId = radioGroupSex.getCheckedRadioButtonId();
        RadioButton rBtn = (RadioButton)findViewById(checkedId);
        String sex = rBtn.getText().toString();
        if (sex.equals("남자")) {
            sex = "M";
        } else {
            sex = "F";
        }

        final String phone1 = inputPhoneNumber1.getText().toString();
        final String phone2 = inputPhoneNumber2.getText().toString();
        final String phone3 = inputPhoneNumber3.getText().toString();

        String mail = DataStoredService.getStoredData(PhoneAuthActivity.this, DataStoredService.STORE_MAIL);
        String memberId = mail.split("@")[0];
        String url = AppController.API_URL + "/members/" + memberId; //멤버개인정보 추가 url
        Log.d("phoneAuth", "mail : " + mail);
        MemberDto memberDto = new MemberDto();
        memberDto.setMail(mail);
        memberDto.setName(name);
        memberDto.setBirthday(birthday);
        memberDto.setSex(sex);
        memberDto.setPhone(phone1 + phone2 + phone3);
        memberDto.setUpdateAt(new Date());

        final String authNumber = getAuthNumber();
        memberDto.setPhoneAuth(authNumber);

        ObjectMapper mapper = new ObjectMapper();
        final JSONObject jsonObject = new JSONObject(mapper.writeValueAsString(memberDto));

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("onResponse", "response.toString() : " + response.toString());
                        try {
                            if ((boolean)response.get("apiSuccess")) {
                                sendSMS(getPhoneNumber(), authNumber);
                                Toast.makeText(PhoneAuthActivity.this, "SMS 발송 완료", Toast.LENGTH_LONG).show();
                                nextActivity();
                            }
                        } catch (JSONException e) {
                            alertMessage("잠시후 다시 시도해주세요.");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alertMessage("잠시후 다시 시도해주세요.");
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

    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }



    private boolean isCorrectPwd(String inputPwd, String dbPwd) {
        if (inputPwd.equals(dbPwd)) {
            return true;
        }
        return false;
    }

    private void nextActivity() {
        Intent intent = new Intent(PhoneAuthActivity.this, PhoneAuthConfirmActivity.class);
        startActivity(intent);
    }

    private Dialog mMainDialog;

    public void onClickBirthdayEditText(View view) {
        mMainDialog = createDialog();
        mMainDialog.show();
    }

    private AlertDialog createDialog() {
        final View innerView = getLayoutInflater().inflate(R.layout.dialog, null);
        final AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("생년월일");
        ab.setView(innerView);

        final NumberPicker year = (NumberPicker) innerView.findViewById(R.id.numberPicker1);
        final NumberPicker month = (NumberPicker) innerView.findViewById(R.id.numberPicker2);
        final NumberPicker day = (NumberPicker) innerView.findViewById(R.id.numberPicker3);

        setDividerColor(year, Color.GREEN);
        year.setMaxValue(2000); // max value 100
        year.setMinValue(1960);   // min value 0
        year.setWrapSelectorWheel(true);
        year.setOnValueChangedListener(this);

        setDividerColor(month, Color.GREEN);
        month.setMaxValue(12); // max value 100
        month.setMinValue(1);   // min value 0
        month.setWrapSelectorWheel(true);
        month.setOnValueChangedListener(this);

        setDividerColor(day, Color.GREEN);
        day.setMaxValue(31); // max value 100
        day.setMinValue(1);   // min value 0
        day.setWrapSelectorWheel(true);
        day.setOnValueChangedListener(this);


        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Log.d("picker", "### >>> " + String.valueOf(year.getValue()) + ":" + String.valueOf(month.getValue()) + ":" + String.valueOf(day.getValue()));
                String stryear = String.valueOf(year.getValue());
                String strmonth = String.valueOf(month.getValue());
                if (strmonth.length() == 1) {
                    strmonth = "0" + strmonth;
                }
                String strday = String.valueOf(day.getValue());
                if (strday.length() == 1) {
                    strday = "0" + strday;
                }
                inputBirthday.setText(stryear + strmonth + strday);
                setDismiss(mMainDialog);
            }
        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mMainDialog);
            }
        });

        return  ab.create();
    }

    private void setDismiss(Dialog dialog){
        if(dialog!=null&&dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Log.i("value is", "" + newVal);

    }

    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
