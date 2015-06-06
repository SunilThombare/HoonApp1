package com.babybong.appting.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hoon on 2015-05-14.
 */
public class ProfileCreateActivity extends BaseActivity implements NumberPicker.OnValueChangeListener {
    private EditText inputNickNmae;
    private EditText inputJob;
    private EditText inputArea1;
    private EditText inputCharacter;
    private EditText inputBloodtype, inputReligion;
    private EditText inputHeight, inputBodyType;
    private Button saveBtn;

    private int areaSelectedIndex = 0;
    private int bloodTypeSelectedIndex = 0;
    private int religionSelectedIndex = 0;

    private int height = 0; //키

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create);
        inputNickNmae = (EditText) findViewById(R.id.inputNickNmae);
        inputArea1 = (EditText) findViewById(R.id.inputArea1);
        inputJob = (EditText) findViewById(R.id.inputJob);
        inputCharacter = (EditText) findViewById(R.id.inputCharacter);
        inputBloodtype = (EditText) findViewById(R.id.inputBloodtype);
        inputReligion = (EditText) findViewById(R.id.inputReligion);
        inputHeight = (EditText) findViewById(R.id.inputHeight);
        inputBodyType = (EditText) findViewById(R.id.inputBodyType);

        saveBtn = (Button) findViewById(R.id.save_btn);

        init();
    }

    private void init() {
        inputBloodtype.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final String items[] = getResources().getStringArray(R.array.bloodType);
                    dialogSelectOption("혈액형", items, 2, inputBloodtype, bloodTypeSelectedIndex);
                }
            }
        });

        inputReligion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final String items[] = getResources().getStringArray(R.array.religion);
                    dialogSelectOption("종교", items, 3, inputReligion, religionSelectedIndex);
                }
            }
        });

        inputArea1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final String items[] = getResources().getStringArray(R.array.area1);
                    dialogSelectOption("지역", items, 1, inputArea1, areaSelectedIndex);
                }
            }
        });

        inputHeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mMainDialog = createHeightDialog();
                    mMainDialog.show();
                }
            }
        });

        inputBodyType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mMainDialog = createBodyTypeDialog();
                    mMainDialog.show();
                }
            }
        });

        inputCharacter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mMainDialog = createCharacterDialog();
                    mMainDialog.show();
                }
            }
        });
    }

    public void onClickArea1Btn(View view) {
        final String items[] = getResources().getStringArray(R.array.area1);
        dialogSelectOption("지역", items, 1, inputArea1, areaSelectedIndex);
    }

    public void onClickBloodTypeBtn(View view) {
        final String items[] = getResources().getStringArray(R.array.bloodType);
        dialogSelectOption("혈액형", items, 2, inputBloodtype, bloodTypeSelectedIndex);
    }

    public void onClickReligionBtn(View view) {
        final String items[] = getResources().getStringArray(R.array.religion);
        dialogSelectOption("종교", items, 3, inputReligion, religionSelectedIndex);
    }

    public void onClickHeight(View view) {
        mMainDialog = createHeightDialog();
        mMainDialog.show();
    }

    public void onClickBodyType(View view) {
        mMainDialog = createBodyTypeDialog();
        mMainDialog.show();
    }

    public void onClickCharacter(View view) {
        mMainDialog = createCharacterDialog();
        mMainDialog.show();
    }

    private void dialogSelectOption(String title, final String[] items, final int type, final EditText btn, int selectedIndex) {
        //final String items[] = getResources().getStringArray(R.array.area1);

        AlertDialog.Builder ab = new AlertDialog.Builder(ProfileCreateActivity.this);
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
            Toast.makeText(ProfileCreateActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void saveBasicProfile() throws Exception {
        final String mail = DataStoredService.getStoredData(ProfileCreateActivity.this, DataStoredService.STORE_MAIL);
        final String nickName = inputNickNmae.getText().toString();
        final String job = inputJob.getText().toString();
        final String area1 = inputArea1.getText().toString();
        final String bloodType = inputBloodtype.getText().toString();
        final String religion = inputReligion.getText().toString();

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
                DataStoredService.storeData(ProfileCreateActivity.this, DataStoredService.STORE_NICKNAME, nickName);
                DataStoredService.storeData(ProfileCreateActivity.this, DataStoredService.STORE_AREA1, area1);
                DataStoredService.storeData(ProfileCreateActivity.this, DataStoredService.STORE_BLOODTYPE, bloodType);
                DataStoredService.storeData(ProfileCreateActivity.this, DataStoredService.STORE_RELIGION, religion);
                DataStoredService.storeData(ProfileCreateActivity.this, DataStoredService.STORE_JOB, job);
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

    private Dialog mMainDialog;

    private AlertDialog createHeightDialog() {
        final View innerView = getLayoutInflater().inflate(R.layout.dialog_height, null);
        final AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("신장 (키)");
        ab.setView(innerView);
        setDismiss(mMainDialog);


        final NumberPicker heightPicker = (NumberPicker) innerView.findViewById(R.id.numberPicker1);

        setDividerColor(heightPicker, Color.GREEN);
        heightPicker.setMaxValue(210); // max value 100
        heightPicker.setMinValue(140);   // min value 0
        heightPicker.setWrapSelectorWheel(true);
        heightPicker.setOnValueChangedListener(this);
        if (height == 0) {
            heightPicker.setValue(170);
        } else {
            heightPicker.setValue(height);
        }

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Log.d("picker", "### >>> " + String.valueOf(heightPicker.getValue()));
                inputHeight.setText(String.valueOf(heightPicker.getValue()));
                height = heightPicker.getValue();
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

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is", "" + newVal);
    }

    private AlertDialog createBodyTypeDialog() {
        final View innerView = getLayoutInflater().inflate(R.layout.dialog_bodytype, null);
        final AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("체형");
        ab.setView(innerView);
        setDismiss(mMainDialog);

        Button bodytype1 = (Button)innerView.findViewById(R.id.bodytype1);
        Button bodytype2 = (Button)innerView.findViewById(R.id.bodytype2);
        Button bodytype3 = (Button)innerView.findViewById(R.id.bodytype3);
        Button bodytype4 = (Button)innerView.findViewById(R.id.bodytype4);
        Button bodytype5 = (Button)innerView.findViewById(R.id.bodytype5);
        Button bodytype6 = (Button)innerView.findViewById(R.id.bodytype6);
        bodytype1.setOnClickListener(onClickListener);
        bodytype2.setOnClickListener(onClickListener);
        bodytype3.setOnClickListener(onClickListener);
        bodytype4.setOnClickListener(onClickListener);
        bodytype5.setOnClickListener(onClickListener);
        bodytype6.setOnClickListener(onClickListener);

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mMainDialog);
            }
        });

        return  ab.create();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn = (Button)view;
            String buttonText = btn.getText().toString();
            inputBodyType.setText(buttonText);
            setDismiss(mMainDialog);
        }
    };

    Set<Integer> checkedSet = new HashSet<>();
    private AlertDialog createCharacterDialog() {
        final View innerView = getLayoutInflater().inflate(R.layout.dialog_character, null);
        final AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("성격");
        ab.setView(innerView);
        setDismiss(mMainDialog);

        final ToggleButton character1 = (ToggleButton)innerView.findViewById(R.id.character1);
        final ToggleButton character2 = (ToggleButton)innerView.findViewById(R.id.character2);
        final ToggleButton character3 = (ToggleButton)innerView.findViewById(R.id.character3);
        final ToggleButton character4 = (ToggleButton)innerView.findViewById(R.id.character4);
        final ToggleButton character5 = (ToggleButton)innerView.findViewById(R.id.character5);
        final ToggleButton character6 = (ToggleButton)innerView.findViewById(R.id.character6);
        final ToggleButton character7 = (ToggleButton)innerView.findViewById(R.id.character7);
        final ToggleButton character8 = (ToggleButton)innerView.findViewById(R.id.character8);
        final ToggleButton character9 = (ToggleButton)innerView.findViewById(R.id.character9);
        final ToggleButton character10 = (ToggleButton)innerView.findViewById(R.id.character10);
        final ToggleButton character11 = (ToggleButton)innerView.findViewById(R.id.character11);
        final ToggleButton character12 = (ToggleButton)innerView.findViewById(R.id.character12);

        Log.d("profileCreate", "checkedSet size" + checkedSet.size());
        for (Integer toggleButtonId : checkedSet) {
            ToggleButton character = (ToggleButton)innerView.findViewById(toggleButtonId);
            character.setChecked(true);
        }

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                StringBuffer message = new StringBuffer();
                checkedSet.clear();
                if (character1.isChecked()) {
                    message.append(character1.getTextOn().toString()).append(", ");
                    checkedSet.add(character1.getId());
                }
                if (character2.isChecked()) {
                    message.append(character2.getTextOn().toString()).append(", ");
                    checkedSet.add(character2.getId());
                }
                if (character3.isChecked()) {
                    message.append(character3.getTextOn().toString()).append(", ");
                    checkedSet.add(character3.getId());
                }
                if (character4.isChecked()) {
                    message.append(character4.getTextOn().toString()).append(", ");
                    checkedSet.add(character4.getId());
                }
                if (character5.isChecked()) {
                    message.append(character5.getTextOn().toString()).append(", ");
                    checkedSet.add(character5.getId());
                }
                if (character6.isChecked()) {
                    message.append(character6.getTextOn().toString()).append(", ");
                    checkedSet.add(character6.getId());
                }
                if (character7.isChecked()) {
                    message.append(character7.getTextOn().toString()).append(", ");
                    checkedSet.add(character7.getId());
                }
                if (character8.isChecked()) {
                    message.append(character8.getTextOn().toString()).append(", ");
                    checkedSet.add(character8.getId());
                }
                if (character9.isChecked()) {
                    message.append(character9.getTextOn().toString()).append(", ");
                    checkedSet.add(character9.getId());
                }
                if (character10.isChecked()) {
                    message.append(character10.getTextOn().toString()).append(", ");
                    checkedSet.add(character10.getId());
                }
                if (character11.isChecked()) {
                    message.append(character11.getTextOn().toString()).append(", ");
                    checkedSet.add(character11.getId());
                }
                if (character12.isChecked()) {
                    message.append(character12.getTextOn().toString()).append(", ");
                    checkedSet.add(character12.getId());
                }
                inputCharacter.setText(message);
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

}
