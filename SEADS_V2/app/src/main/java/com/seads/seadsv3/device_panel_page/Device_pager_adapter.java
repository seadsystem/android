package com.seads.seadsv3.device_panel_page;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seads.seadsv3.graph.RoomVisualizationFragment;

/**
 * Created by Home on 2/13/18.
 */

public class Device_pager_adapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 1;
    private String tabTitles[] = new String[] { "Energy Stats"};
    private Context mContext;
    private String room;

    public Device_pager_adapter(FragmentManager fm, Context context, String room) {
        super(fm);
        this.mContext = context;
        this.room = room;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        RoomVisualizationFragment roomVisualizationFragment = new RoomVisualizationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("device",this.room);
        roomVisualizationFragment.setArguments(bundle);
        return roomVisualizationFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
