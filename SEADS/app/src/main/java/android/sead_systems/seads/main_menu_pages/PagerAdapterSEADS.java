package android.sead_systems.seads.main_menu_pages;

import android.content.Context;
import android.sead_systems.seads.R;
import android.sead_systems.seads.main_menu.EnumNavBarNames;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jungk on 2/11/2018.
 */

public class PagerAdapterSEADS extends FragmentPagerAdapter {
    private int PAGE_COUNT = 3;
    private String tabTitles[];// = new String[] { "Devices", "Rooms", "Overview" };
    private Context mContext;

    public PagerAdapterSEADS(FragmentManager fm, Context context) {
        super(fm);
        this.PAGE_COUNT = EnumNavBarNames.values().length;
        this.tabTitles = new String[this.PAGE_COUNT];
        int index = 0;
        for(EnumNavBarNames x : EnumNavBarNames.values()) {
            this.tabTitles[index] = x.getTitle();
            index++;
        }
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
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        // Generate title based on item position
//        return tabTitles[position];
//    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(mContext).inflate(R.layout.tab_main, null);
        TextView tv = (TextView) v.findViewById(R.id.tab_title);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) v.findViewById(R.id.tab_image);
        img.setImageResource(R.drawable.rounded_button);
        return v;
    }

}
