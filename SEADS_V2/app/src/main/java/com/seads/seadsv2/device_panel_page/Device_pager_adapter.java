package com.seads.seadsv2.device_panel_page;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seads.seadsv2.graph.TabFragment5;

/**
 * Created by Home on 2/13/18.
 */

public class Device_pager_adapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 1;
    private String tabTitles[] = new String[] { "Energy Stats"};
    private Context mContext;

    public Device_pager_adapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        TabFragment5 tabFragment5 = new TabFragment5();
        return tabFragment5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
