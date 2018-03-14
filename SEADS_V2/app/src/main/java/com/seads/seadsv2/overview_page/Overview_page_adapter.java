package com.seads.seadsv2.overview_page;

/**
 * Created by root on 3/2/18.
 */


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seads.seadsv2.graph.TabFragment6;

/**
 * Created by Home on 2/13/18.
 */

public class Overview_page_adapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 1;
    private String tabTitles[] = new String[] { "Overview"};
    private Context mContext;

    public Overview_page_adapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        TabFragment6 tabFragment6 = new TabFragment6();
        return tabFragment6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
