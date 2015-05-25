package com.babybong.appting.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.babybong.appting.GcmMainActivity;

import com.babybong.appting.R;
import com.babybong.appting.app.AppController;
import com.babybong.appting.BaseActivity;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 *  프로필 수정 메인화면
 */
public class ProfileEditActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        onCreateTracker();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClickImageEditBtn(View view) {
        nextActivity(ImageEditActivity.class);
    }

    public void onClickBasicEditBtn(View view) {
        nextActivity(ProfileBasicEditActivity.class);
    }

    public void onClickSelfIntroductionEditBtn(View view) {
        nextActivity(IntroductionEditActivity.class);
    }

    public void onClickSelfIntroductionChoiceEditBtn(View view) {
        nextActivity(GcmMainActivity.class);
    }

    public void nextActivity(Class zlass) {
        Intent intent = new Intent(ProfileEditActivity.this, zlass);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    private void onCreateTracker() {
        Tracker tracker = ((AppController)getApplication()).getTracker();
        // Builder parameters can overwrite the screen name set on the tracker.
        tracker.setScreenName("ProfileEditActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }
}
