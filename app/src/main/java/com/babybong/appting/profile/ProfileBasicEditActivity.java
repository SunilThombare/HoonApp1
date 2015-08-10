package com.babybong.appting.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.babybong.appting.common.ApiAddress;
import com.babybong.appting.common.AreaDialogFragment;
import com.babybong.appting.common.AreaDialogInterface;
import com.babybong.appting.login.PhoneAuthActivity;
import com.babybong.appting.login.service.DataStoredService;
import com.babybong.appting.main.MainActivity;
import com.babybong.appting.model.MemberDto;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by hoon on 2015-05-14.
 */
public class ProfileBasicEditActivity extends BaseActivity implements NumberPicker.OnValueChangeListener, AreaDialogInterface {
    private EditText inputNickNmae;
    private EditText inputJob;
    private EditText inputHobby;
    private EditText inputArea1, inputArea2;
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
        setContentView(R.layout.activity_profile_basic_edit);
        inputNickNmae = (EditText) findViewById(R.id.inputNickNmae);
        inputArea1 = (EditText) findViewById(R.id.inputArea1);
        inputArea2 = (EditText) findViewById(R.id.inputArea2);
        inputJob = (EditText) findViewById(R.id.inputJob);
        inputHobby = (EditText) findViewById(R.id.inputHobby);
        inputCharacter = (EditText) findViewById(R.id.inputCharacter);
        inputBloodtype = (EditText) findViewById(R.id.inputBloodtype);
        inputReligion = (EditText) findViewById(R.id.inputReligion);
        inputHeight = (EditText) findViewById(R.id.inputHeight);
        inputBodyType = (EditText) findViewById(R.id.inputBodyType);

        saveBtn = (Button) findViewById(R.id.save_btn);

        init();
        getProfileDataFromServer();
    }

    private void init() {
        inputArea1.setOnClickListener(onClickListener);
        inputJob.setOnClickListener(onClickListener);
        inputHobby.setOnClickListener(onClickListener);
        inputCharacter.setOnClickListener(onClickListener);
        inputBloodtype.setOnClickListener(onClickListener);
        inputReligion.setOnClickListener(onClickListener);
        inputHeight.setOnClickListener(onClickListener);
        inputBodyType.setOnClickListener(onClickListener);

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
                    /*final String items[] = getResources().getStringArray(R.array.area1);
                    dialogSelectOption("지역", items, 1, inputArea1, areaSelectedIndex);*/
                    AreaDialogFragment dialog = new AreaDialogFragment();
                    dialog.show(getFragmentManager(), "areaDialog");
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

        inputJob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mMainDialog = createJobDialog();
                    mMainDialog.show();
                }
            }
        });

        inputHobby.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mMainDialog = createHobbyDialog();
                    mMainDialog.show();
                }
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.inputArea1:
                   /* final String items[] = getResources().getStringArray(R.array.area1);
                    dialogSelectOption("지역", items, 1, inputArea1, areaSelectedIndex);*/
                    AreaDialogFragment dialog = new AreaDialogFragment();
                    dialog.show(getFragmentManager(), "areaDialog");
                    break;
                case R.id.inputJob:
                    mMainDialog = createJobDialog();
                    mMainDialog.show();
                    break;
                case R.id.inputHobby:
                    mMainDialog = createHobbyDialog();
                    mMainDialog.show();
                    break;
                case R.id.inputCharacter:
                    mMainDialog = createCharacterDialog();
                    mMainDialog.show();
                    break;
                case R.id.inputBloodtype:
                    final String bloodTypeItems[] = getResources().getStringArray(R.array.bloodType);
                    dialogSelectOption("혈액형", bloodTypeItems, 2, inputBloodtype, bloodTypeSelectedIndex);
                    break;
                case R.id.inputReligion:
                    final String religionItems[] = getResources().getStringArray(R.array.religion);
                    dialogSelectOption("종교", religionItems, 3, inputReligion, religionSelectedIndex);
                    break;
                case R.id.inputHeight:
                    mMainDialog = createHeightDialog();
                    mMainDialog.show();
                    break;
                case R.id.inputBodyType:
                    mMainDialog = createBodyTypeDialog();
                    mMainDialog.show();
                    break;
                default:
                    break;
            }

        }
    };

    public void onClickArea1Btn(View view) {
        /*final String items[] = getResources().getStringArray(R.array.area1);
        dialogSelectOption("지역", items, 1, inputArea1, areaSelectedIndex);*/
        AreaDialogFragment dialog = new AreaDialogFragment();
        dialog.show(getFragmentManager(), "areaDialog");
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

    public void onClickJob(View view) {
        mMainDialog = createJobDialog();
        mMainDialog.show();
    }

    public void onClickHobby(View view) {
        mMainDialog = createHobbyDialog();
        mMainDialog.show();
    }

    private void dialogSelectOption(String title, final String[] items, final int type, final EditText btn, int selectedIndex) {
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

    /**
     * 저장하기
     * @param view
     */
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
        final String area1 = inputArea1.getText().toString();
        final String area2 = inputArea2.getText().toString();
        final String job = inputJob.getText().toString();
        final String hobby = inputHobby.getText().toString();  //추가
        final String character = inputCharacter.getText().toString();  //추가
        final String bloodType = inputBloodtype.getText().toString();
        final String religion = inputReligion.getText().toString();
        final String height = inputHeight.getText().toString();  //추가
        final String bodyType = inputBodyType.getText().toString();  //추가

        String memberId = mail.split("@")[0];
        //String url = AppController.API_URL + "/members/basicProfile/" + memberId; //멤버개인정보 추가 url
        String url = ApiAddress.BASIC_PROFILE_CREATE + "/" + memberId;
        Log.d("phoneAuth", "mail : " + mail);
        MemberDto memberDto = new MemberDto();
        memberDto.setMail(mail); //key
        memberDto.setNickName(nickName);
        memberDto.setAddress1(area1);
        memberDto.setAddress2(area2);
        memberDto.setJob(job);
        memberDto.setHobby(hobby);
        memberDto.setCharacter(character);
        memberDto.setBloodType(bloodType);
        memberDto.setReligion(religion);
        memberDto.setHeight(height);
        memberDto.setBodyType(bodyType);

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
                        //dataStore();
                        finish();
                    } else {
                        alertMessage("잠시후 다시 시도하세요.");
                    }
                } catch (JSONException e) {
                    alertMessage("담당자에게 문의하세요.");
                    Log.e("login", "JSONException error : " + e.toString());
                }
            }

            /*private void dataStore() {
                DataStoredService.storeData(ProfileBasicEditActivity.this, DataStoredService.STORE_NICKNAME, nickName);
                DataStoredService.storeData(ProfileBasicEditActivity.this, DataStoredService.STORE_AREA1, area1);
                DataStoredService.storeData(ProfileBasicEditActivity.this, DataStoredService.STORE_BLOODTYPE, bloodType);
                DataStoredService.storeData(ProfileBasicEditActivity.this, DataStoredService.STORE_RELIGION, religion);
                DataStoredService.storeData(ProfileBasicEditActivity.this, DataStoredService.STORE_JOB, job);
            }*/
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
        final View innerView = getLayoutInflater().inflate(R.layout.dialog_body_type, null);
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
        bodytype1.setOnClickListener(onClickBodyTypeListener);
        bodytype2.setOnClickListener(onClickBodyTypeListener);
        bodytype3.setOnClickListener(onClickBodyTypeListener);
        bodytype4.setOnClickListener(onClickBodyTypeListener);
        bodytype5.setOnClickListener(onClickBodyTypeListener);
        bodytype6.setOnClickListener(onClickBodyTypeListener);

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mMainDialog);
            }
        });

        return  ab.create();
    }

    private View.OnClickListener onClickBodyTypeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn = (Button)view;
            String buttonText = btn.getText().toString();
            inputBodyType.setText(buttonText);
            setDismiss(mMainDialog);
        }
    };

    Set<Integer> checkedCharacterSet = new HashSet<>();
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

        Log.d("profileCreate", "checkedCharacterSet size" + checkedCharacterSet.size());
        for (Integer toggleButtonId : checkedCharacterSet) {
            ToggleButton tbtn = (ToggleButton)innerView.findViewById(toggleButtonId);
            tbtn.setChecked(true);
        }

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                StringBuffer message = new StringBuffer();
                checkedCharacterSet.clear();
                if (character1.isChecked()) {
                    message.append(character1.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character1.getId());
                }
                if (character2.isChecked()) {
                    message.append(character2.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character2.getId());
                }
                if (character3.isChecked()) {
                    message.append(character3.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character3.getId());
                }
                if (character4.isChecked()) {
                    message.append(character4.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character4.getId());
                }
                if (character5.isChecked()) {
                    message.append(character5.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character5.getId());
                }
                if (character6.isChecked()) {
                    message.append(character6.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character6.getId());
                }
                if (character7.isChecked()) {
                    message.append(character7.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character7.getId());
                }
                if (character8.isChecked()) {
                    message.append(character8.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character8.getId());
                }
                if (character9.isChecked()) {
                    message.append(character9.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character9.getId());
                }
                if (character10.isChecked()) {
                    message.append(character10.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character10.getId());
                }
                if (character11.isChecked()) {
                    message.append(character11.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character11.getId());
                }
                if (character12.isChecked()) {
                    message.append(character12.getTextOn().toString()).append(", ");
                    checkedCharacterSet.add(character12.getId());
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

    private AlertDialog createJobDialog() {
        final View innerView = getLayoutInflater().inflate(R.layout.dialog_job, null);
        final AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("직업.분야");
        ab.setView(innerView);
        setDismiss(mMainDialog);

        Button job1 = (Button)innerView.findViewById(R.id.job1);
        Button job2 = (Button)innerView.findViewById(R.id.job2);
        Button job3 = (Button)innerView.findViewById(R.id.job3);
        Button job4 = (Button)innerView.findViewById(R.id.job4);
        Button job5 = (Button)innerView.findViewById(R.id.job5);
        Button job6 = (Button)innerView.findViewById(R.id.job6);
        Button job7 = (Button)innerView.findViewById(R.id.job7);
        Button job8 = (Button)innerView.findViewById(R.id.job8);
        Button job9 = (Button)innerView.findViewById(R.id.job9);
        Button job10 = (Button)innerView.findViewById(R.id.job10);
        Button job11 = (Button)innerView.findViewById(R.id.job11);
        Button job12 = (Button)innerView.findViewById(R.id.job12);
        Button job13 = (Button)innerView.findViewById(R.id.job13);
        Button job14 = (Button)innerView.findViewById(R.id.job14);
        Button job15 = (Button)innerView.findViewById(R.id.job15);
        Button job16 = (Button)innerView.findViewById(R.id.job16);
        job1.setOnClickListener(onClickJobListener);
        job2.setOnClickListener(onClickJobListener);
        job3.setOnClickListener(onClickJobListener);
        job4.setOnClickListener(onClickJobListener);
        job5.setOnClickListener(onClickJobListener);
        job6.setOnClickListener(onClickJobListener);
        job7.setOnClickListener(onClickJobListener);
        job8.setOnClickListener(onClickJobListener);
        job9.setOnClickListener(onClickJobListener);
        job10.setOnClickListener(onClickJobListener);
        job11.setOnClickListener(onClickJobListener);
        job12.setOnClickListener(onClickJobListener);
        job13.setOnClickListener(onClickJobListener);
        job14.setOnClickListener(onClickJobListener);
        job15.setOnClickListener(onClickJobListener);
        job16.setOnClickListener(onClickJobListener);

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mMainDialog);
            }
        });

        return  ab.create();
    }

    private View.OnClickListener onClickJobListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn = (Button)view;
            String buttonText = btn.getText().toString();
            inputJob.setText(buttonText);
            setDismiss(mMainDialog);
        }
    };

    Set<Integer> checkedHobbySet = new HashSet<>();
    private AlertDialog createHobbyDialog() {
        final View innerView = getLayoutInflater().inflate(R.layout.dialog_hobby, null);
        final AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("취미");
        ab.setView(innerView);
        setDismiss(mMainDialog);

        final ToggleButton hobby1 = (ToggleButton)innerView.findViewById(R.id.hobby1);
        final ToggleButton hobby2 = (ToggleButton)innerView.findViewById(R.id.hobby2);
        final ToggleButton hobby3 = (ToggleButton)innerView.findViewById(R.id.hobby3);
        final ToggleButton hobby4 = (ToggleButton)innerView.findViewById(R.id.hobby4);
        final ToggleButton hobby5 = (ToggleButton)innerView.findViewById(R.id.hobby5);
        final ToggleButton hobby6 = (ToggleButton)innerView.findViewById(R.id.hobby6);
        final ToggleButton hobby7 = (ToggleButton)innerView.findViewById(R.id.hobby7);
        final ToggleButton hobby8 = (ToggleButton)innerView.findViewById(R.id.hobby8);
        final ToggleButton hobby9 = (ToggleButton)innerView.findViewById(R.id.hobby9);
        final ToggleButton hobby10 = (ToggleButton)innerView.findViewById(R.id.hobby10);
        final ToggleButton hobby11 = (ToggleButton)innerView.findViewById(R.id.hobby11);
        final ToggleButton hobby12 = (ToggleButton)innerView.findViewById(R.id.hobby12);
        final ToggleButton hobby13 = (ToggleButton)innerView.findViewById(R.id.hobby13);
        final ToggleButton hobby14 = (ToggleButton)innerView.findViewById(R.id.hobby14);
        final ToggleButton hobby15 = (ToggleButton)innerView.findViewById(R.id.hobby15);
        final ToggleButton hobby16 = (ToggleButton)innerView.findViewById(R.id.hobby16);

        Log.d("profileCreate", "checkedHobbySet size" + checkedHobbySet.size());
        for (Integer toggleButtonId : checkedHobbySet) {
            ToggleButton tbtn = (ToggleButton)innerView.findViewById(toggleButtonId);
            tbtn.setChecked(true);
        }

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                StringBuffer message = new StringBuffer();
                checkedHobbySet.clear();
                if (hobby1.isChecked()) {
                    message.append(hobby1.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby1.getId());
                }
                if (hobby2.isChecked()) {
                    message.append(hobby2.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby2.getId());
                }
                if (hobby3.isChecked()) {
                    message.append(hobby3.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby3.getId());
                }
                if (hobby4.isChecked()) {
                    message.append(hobby4.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby4.getId());
                }
                if (hobby5.isChecked()) {
                    message.append(hobby5.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby5.getId());
                }
                if (hobby6.isChecked()) {
                    message.append(hobby6.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby6.getId());
                }
                if (hobby7.isChecked()) {
                    message.append(hobby7.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby7.getId());
                }
                if (hobby8.isChecked()) {
                    message.append(hobby8.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby8.getId());
                }
                if (hobby9.isChecked()) {
                    message.append(hobby9.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby9.getId());
                }
                if (hobby10.isChecked()) {
                    message.append(hobby10.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby10.getId());
                }
                if (hobby11.isChecked()) {
                    message.append(hobby11.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby11.getId());
                }
                if (hobby12.isChecked()) {
                    message.append(hobby12.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby12.getId());
                }
                if (hobby13.isChecked()) {
                    message.append(hobby13.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby13.getId());
                }
                if (hobby14.isChecked()) {
                    message.append(hobby14.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby14.getId());
                }
                if (hobby15.isChecked()) {
                    message.append(hobby15.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby15.getId());
                }
                if (hobby16.isChecked()) {
                    message.append(hobby16.getTextOn().toString()).append(", ");
                    checkedHobbySet.add(hobby16.getId());
                }
                inputHobby.setText(message);
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

    /**
     * 데이터가져오기
     */
    private void getProfileDataFromServer() {
        final String mail = DataStoredService.getStoredData(this, DataStoredService.STORE_MAIL);
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
                        String hobby = memberDto.getString("hobby");
                        String character = memberDto.getString("character");
                        String religion = memberDto.getString("religion");
                        String bloodType = memberDto.getString("bloodType");
                        String address1 = memberDto.getString("address1");
                        String height = memberDto.getString("height");
                        String bodyType = memberDto.getString("bodyType");

                        inputNickNmae.setText(nickName);
                        inputArea1.setText(address1);
                        inputJob.setText(job);
                        inputHobby.setText(hobby);
                        inputCharacter.setText(character);
                        inputBloodtype.setText(bloodType);
                        inputReligion.setText(religion);
                        inputHeight.setText(height);
                        inputBodyType.setText(bodyType);

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

    @Override
    public void selectedArea(String area1, String area2) {
        inputArea1.setText(area1);
        inputArea2.setText(area2);
    }
}
