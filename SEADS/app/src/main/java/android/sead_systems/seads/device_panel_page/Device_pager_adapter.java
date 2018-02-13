package android.sead_systems.seads.device_panel_page;

import android.content.Context;
import android.sead_systems.seads.graph.TabFragment5;
import android.sead_systems.seads.page_management_main_menu.PageFragmentConfig;
import android.sead_systems.seads.page_management_main_menu.PageFragmentFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Home on 2/13/18.
 */

public class Device_pager_adapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Energy Stats", "Cost"};
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
