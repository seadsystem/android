package com.seads.seadsv3.main_menu_pages.rooms_fragment;

/**
 * View information to populate the room view recyclerview.
 */

public class RoomViewInfo {
    private String mTitle;
    private String mCostInPastDay;
    private int mDevicesInRoom;

    public RoomViewInfo (String title, int devices) {
        mTitle = title;
        mDevicesInRoom = devices;
        mCostInPastDay = "Cost in the past day: N/A";
    }

    public RoomViewInfo (String title, int devices, String costInPastDay) {
        mTitle = title;
        mCostInPastDay = costInPastDay;
        mDevicesInRoom = devices;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setCostInPastDay(String costInPastDay) {
        mCostInPastDay = costInPastDay;
    }

    public String getCostInPastDay() {
        return mCostInPastDay;
    }

    public int getDevicesInRoom() {
        return mDevicesInRoom;
    }
    private void setDevicesInRoom(int devices) {
        mDevicesInRoom = devices;
    }

}
