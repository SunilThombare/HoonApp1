package com.babybong.appting.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.babybong.appting.R;
import com.viewpagerindicator.IconPagerAdapter;

class TestFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] { "This", "앱팅", "내프로필", "설정", };
    protected static final int[] ICONS = new int[] {
            R.drawable.perm_group_calendar,
            R.drawable.perm_group_camera,
            R.drawable.perm_group_device_alarms,
            R.drawable.perm_group_location
    };

    private int mCount = CONTENT.length;

    public TestFragmentAdapter(FragmentManager fm) {
        super(fm);
        createFragments();
    }

    private Fragment[] fragments = new Fragment[4];

    private void createFragments() {
        fragments[0] = TestFragment.newInstance(CONTENT[0]);
        fragments[1] = TodayTingFragment.newInstance(CONTENT[1]);
        fragments[2] = MyProfileFragment.newInstance(CONTENT[2]);
        fragments[3] = SettingFragment.newInstance(CONTENT[3]);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("hoon", "getItem : " + position);
        return fragments[position];
       /* if (position == 1) {
            return TodayTingFragment.newInstance(CONTENT[position % CONTENT.length]);
        } else if (position == 2) {
            return MyProfileFragment.newInstance(CONTENT[position % CONTENT.length]);
        } else if (position == 3) {
            return SettingFragment.newInstance(CONTENT[position % CONTENT.length]);
        }
        return TestFragment.newInstance(CONTENT[position % CONTENT.length]);*/
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return TestFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}