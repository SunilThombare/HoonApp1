package com.babybong.appting.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.babybong.appting.R;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by hoon on 2015-05-10.
 */
public class MainActivity extends FragmentActivity {
    private static final int LOADING_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);

        FragmentPagerAdapter adapter = new TestFragmentAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(LOADING_COUNT);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        //indicator.setBackgroundColor(Color.parseColor("#32cd32"));  //32cd32 //228b22

    }
}