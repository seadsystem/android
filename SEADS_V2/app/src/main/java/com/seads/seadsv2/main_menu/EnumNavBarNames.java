package com.seads.seadsv2.main_menu;

import com.seads.seadsv2.R;

public enum EnumNavBarNames {
    DEVICES(0, "Devices", R.layout.fragment_device_main_menu),
    ROOMS(1, "Rooms", R.layout.fragment_room_main_menu),
    OVERVIEW(2, "Overview", R.layout.fragment_overview_main_menu),
    AWARDS(3, "Awards", R.layout.fragment_awards_main_menu),
    SETTINGS(4, "Settings", R.layout.fragment_settings_main_menu);

    private final int index;
    private final String title;
    private final int layoutId;

    EnumNavBarNames(int index, String title, int id) {
        this.index = index;
        this.title = title;
        this.layoutId = id;
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
}
