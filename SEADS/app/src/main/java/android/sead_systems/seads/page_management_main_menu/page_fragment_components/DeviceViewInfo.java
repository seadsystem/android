package android.sead_systems.seads.page_management_main_menu.page_fragment_components;

/**
 * Use an array like structure to populate the recycler view of devices
 */
public class DeviceViewInfo {
    private String mDeviceName;
    private String mCostInPastDay;
    private boolean header;
    private String mTitle;
    // Reference to bitmap?

    /**
     * This constructor should only be used if this will be used to store a title
     * @param title Title of the section
     * @param header should always be true, dummy param that doesn't affect anything
     */
    public DeviceViewInfo(String title, boolean header) {
        this.header = true;
        this.mTitle = title;
    }

    /**
     * Constructor: If no info is given, we use all the defaults
     */
    public DeviceViewInfo() {
        mDeviceName = "Device Name Unknown";
        mCostInPastDay = "Cost in the past day: N/A";
        this.header = false;
    }

    /**
     * Constructor: If name is given we'll place the name. Cost is N/A and may be determined later
     */
    public DeviceViewInfo(String deviceName) {
        mDeviceName = deviceName;
        mCostInPastDay = "Cost in the past day: N/A";
        this.header = false;
    }

    /**
     * Constructor: Both pieces of data already determined, if app stays on for a long time
     * this should be updated or the data will eventually go stale
     */
    public DeviceViewInfo(String deviceName, String costInPastDay) {
        mDeviceName = deviceName;
        mCostInPastDay = costInPastDay;
        this.header = false;
    }

    /**
     * getter for device name
     * @return the device name
     */
    public String getDeviceName() {
        return mDeviceName;
    }

    /**
     * getter for cost in past day
     * @return the amount of money the device has used in the past 24 hours
     */
    public String getCostInPastDay() {
        return mCostInPastDay;
    }

    /**
     * getter for header title
     * @return the title of a header
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * getter for whether it's a header
     * @return is the item a header
     */
    public boolean isHeader() {
        return this.header;
    }

    private void calculateCostInPastDay(String costInPastDay) {

    }

}
