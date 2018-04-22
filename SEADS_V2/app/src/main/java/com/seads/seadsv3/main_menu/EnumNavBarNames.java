package com.seads.seadsv3.main_menu;

import com.seads.seadsv3.R;

public enum EnumNavBarNames {
    DEVICES(0, "Devices", R.layout.fragment_device_main_menu, R.drawable.home),
    ROOMS(1, "Rooms", R.layout.fragment_room_main_menu, R.drawable.rooms),
    OVERVIEW(2, "Overview", R.layout.fragment_overview_main_menu,R.drawable.overview),
    AWARDS(3, "Awards", R.layout.fragment_awards_main_menu,R.drawable.awards),
    SETTINGS(4, "Settings", R.layout.fragment_settings_main_menu,R.drawable.settings);

    private final int index;
    private final String title;
    private final int layoutId;
    private final int iconName;

    EnumNavBarNames(int index, String title, int id, int iconName) {
        this.index = index;
        this.title = title;
        this.layoutId = id;
        this.iconName = iconName;
    }

    public int getIndex() {
        return this.index;
    }

    public String getTitle() {
        return this.title;
    }

    public int getLayoutId() {
        return this.layoutId;
    }

    public int getIconName() { return this.iconName;}

}

