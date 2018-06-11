package com.seads.seadsv3.main_menu_pages.rooms_fragment;

/**
 * View information to populate the room view recyclerview.
 */

public class RoomViewInfo {
    private String mTitle;
    private int mDevicesInRoom;

    public RoomViewInfo (String title, int devices) {
        mTitle = title;
        mDevicesInRoom = devices;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getDevicesInRoom() {
        return mDevicesInRoom;
    }
    private void setDevicesInRoom(int devices) {
        mDevicesInRoom = devices;
    }

}
