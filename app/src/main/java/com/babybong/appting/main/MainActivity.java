package com.babybong.appting.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TabPageIndicator;

import info.androidhive.appting.R;

/**
 * Created by hoon on 2015-05-10.
 */
public class MainActivity extends FragmentActivity {
    private static final String[] CONTENT = new String[] { "오늘의미팅", "내프로필", "기타", "설정"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs);

        //FragmentPagerAdapter adapter = new GoogleMusicAdapter(getSupportFragmentManager());
        FragmentPagerAdapter adapter = new TestFragmentAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }

    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }
}
