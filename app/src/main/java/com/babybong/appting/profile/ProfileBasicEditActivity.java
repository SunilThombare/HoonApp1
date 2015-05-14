package com.babybong.appting.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.babybong.appting.BaseActivity;

import info.androidhive.appting.R;

/**
 * Created by hoon on 2015-05-14.
 */
public class ProfileBasicEditActivity extends BaseActivity {
    private EditText inputNmae;
    private EditText inputYear, inputMonth, inputDay;
    private RadioGroup radioGroupSex;
    private EditText inputPhoneNumber1, inputPhoneNumber2, inputPhoneNumber3;
    private Button bloodTypeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_basic_edit);
        bloodTypeBtn = (Button) findViewById(R.id.input_bloodtype);
        /*areaBtn = (Button) findViewById(R.id.input_name);
        inputYear = (EditText) findViewById(R.id.input_year);
        inputMonth = (EditText) findViewById(R.id.input_month);
        inputDay = (EditText) findViewById(R.id.input_day);
        radioGroupSex = (RadioGroup) findViewById(R.id.radiogroup_sex);
        inputPhoneNumber1 = (EditText) findViewById(R.id.input_phone_number1);
        inputPhoneNumber2 = (EditText) findViewById(R.id.input_phone_number2);
        inputPhoneNumber3 = (EditText) findViewById(R.id.input_phone_number3);

        phoneAuthBtn = (Button) findViewById(R.id.phone_auth_btn);*/

        /*editTextListener(inputYear, inputMonth, 4);
        editTextListener(inputMonth, inputDay, 2);

        editTextListener(inputPhoneNumber1, inputPhoneNumber2, 3);
        editTextListener(inputPhoneNumber2, inputPhoneNumber3, 4);
        editTextListener(inputPhoneNumber3, phoneAuthBtn, 4);*/

    }

    public void onClickBloodTypeBtn(View view) {
        dialogSelectOption();
    }

    private int selectedIndex = -1;
    private void dialogSelectOption() {
        final String items[] = { "A형", "B형", "AB형", "O형" };

        AlertDialog.Builder ab = new AlertDialog.Builder(ProfileBasicEditActivity.this);
        ab.setTitle("Title");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        selectedIndex = whichButton;
                        Log.d("log", "whichButton : " + whichButton);

                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        bloodTypeBtn.setText(items[selectedIndex]);
                        Log.d("log", "ok whichButton : " + whichButton);
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d("log", "cancel whichButton : " + whichButton);
                    }
                });
        ab.show();
    }
}
