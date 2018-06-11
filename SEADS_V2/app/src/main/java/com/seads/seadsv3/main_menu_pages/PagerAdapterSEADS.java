package com.seads.seadsv3.main_menu_pages;

import android.content.Context;
import com.seads.seadsv3.R;
import com.seads.seadsv3.SeadsDevice;
import com.seads.seadsv3.main_menu.EnumNavBarNames;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class PagerAdapterSEADS extends FragmentPagerAdapter {
    private int PAGE_COUNT;
    private String tabTitles[];// = new String[] { "Devices", "Rooms", "Overview" };
    private int tabIcons[]; // = new int[] {R.drawable.home, R.drawable.device, etc};
    private Context mContext;
    private SeadsDevice seadsDevice;

    /**
     * Adapter for fragment manager, pass in the Activity context to instantiate the factory
     * @param fm
     * @param context
     */
    public PagerAdapterSEADS(FragmentManager fm, Context context, SeadsDevice seadsDevice) {
        super(fm);
        this.PAGE_COUNT = EnumNavBarNames.values().length;
        this.tabTitles = new String[this.PAGE_COUNT];
        this.tabIcons = new int[this.PAGE_COUNT];
        int index = 0;
        for(EnumNavBarNames x : EnumNavBarNames.values()) {
            this.tabTitles[index] = x.getTitle();
            this.tabIcons[index] = x.getIconName();
            index++;
        }
        this.mContext = context;
        this.seadsDevice = seadsDevice;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        PageFragmentConfig pageFragmentConfig = new PageFragmentConfig(position);
        return PageFragmentFactory.newInstance(pageFragmentConfig, seadsDevice);
    }


    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_main, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_title);
        textView.setText(tabTitles[position]);
        ImageView img = (ImageView) view.findViewById(R.id.tab_image);
        // set image resource based on position
        img.setImageResource(tabIcons[position]);
        return view;
    }

}
