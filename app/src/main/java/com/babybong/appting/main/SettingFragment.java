package com.babybong.appting.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.babybong.appting.R;
import com.babybong.appting.login.LoginActivity;
import com.babybong.appting.login.LoginInfoActivity;
import com.babybong.appting.login.service.DataStoredService;
import com.babybong.appting.profile.ProfileEditActivity;

public final class SettingFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    private Button loginInfoBtn;
    private Button profileEditBtn;
    private Button logoutBtn;

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    public static SettingFragment newInstance(String content) {
        SettingFragment fragment = new SettingFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        logoutBtn = (Button)view.findViewById(R.id.logout_btn);
        loginInfoBtn = (Button)view.findViewById(R.id.login_info_btn);
        profileEditBtn = (Button)view.findViewById(R.id.profile_edit_btn);

        loginInfoBtn.setOnClickListener(btnListener);
        profileEditBtn.setOnClickListener(btnListener);
        logoutBtn.setOnClickListener(btnListener);
        return view;
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.logout_btn:
                    Log.d("SettingFragment", "로그아웃");
                    loginInfoClear();
                    loginActivity();
                    break;
                case R.id.login_info_btn:
                    startActivity(LoginInfoActivity.class);
                    break;
                case R.id.profile_edit_btn:
                    startActivity(ProfileEditActivity.class);
                    break;
            }

        }
    };

    private void loginInfoClear() {
        DataStoredService.loginInfoClear(getActivity());
    }

    private void loginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startActivity(Class zlass) {
        Intent intent = new Intent(getActivity(), zlass);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
