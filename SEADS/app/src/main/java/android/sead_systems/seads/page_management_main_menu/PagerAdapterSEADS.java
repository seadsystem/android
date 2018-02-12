package android.sead_systems.seads.page_management_main_menu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jungk on 2/11/2018.
 */

public class PagerAdapterSEADS extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Devices", "Rooms", "Overview" };
    private Context mContext;

    public PagerAdapterSEADS(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        PageFragmentConfig pageFragmentConfig = new PageFragmentConfig(position);
        return PageFragmentFactory.newInstance(pageFragmentConfig);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
