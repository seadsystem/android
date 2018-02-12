package android.sead_systems.seads.page_management_main_menu.page_fragment_components;


public class DeviceViewInfo {
    private String mDeviceName;
    private String mCostInPastDay;
    // Reference to bitmap?

    /**
     * Constructor: If no info is given, we use all the defaults
     */
    public DeviceViewInfo() {
        mDeviceName = "Device Name Unknown";
        mCostInPastDay = "Cost in the past day: N/A";
    }

    /**
     * Constructor: If name is given we'll place the name. Cost is N/A and may be determined later
     */
    public DeviceViewInfo(String deviceName) {
        mDeviceName = deviceName;
        mCostInPastDay = "Cost in the past day: N/A";
    }

    /**
     * Constructor: Both pieces of data already determined, if app stays on for a long time
     * this should be updated or the data will eventually go stale
     */
    public DeviceViewInfo(String deviceName, String costInPastDay) {
        mDeviceName = deviceName;
        mCostInPastDay = costInPastDay;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public String getCostInPastDay() {
        return mCostInPastDay;
    }

    private void calculateCostInPastDay(String costInPastDay) {

    }

}
