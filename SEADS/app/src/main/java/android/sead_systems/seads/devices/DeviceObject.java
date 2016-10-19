package android.sead_systems.seads.devices;

/** Created by talal.abouhaiba on 10/19/2016. */

/**
 * A container for the attributes of a single device.
 */
public class DeviceObject {

    private String mDeviceName;

    private boolean mDeviceStatus;
    private long mDeviceUsage;

    public DeviceObject(String deviceName, boolean currentStatus, long usage) {
        mDeviceName = deviceName;
        mDeviceStatus = currentStatus;
        mDeviceUsage = usage;
    }

    public void updateDeviceUsage(long updatedUsage) {
        mDeviceUsage = updatedUsage;
    }

    public void updateDeviceStatus(boolean updatedStatus) {
        mDeviceStatus = updatedStatus;
    }

    public long getDeviceUsage() {
        return mDeviceUsage;
    }

    public boolean getDeviceStatus() {
        return mDeviceStatus;
    }

    @Override
    public String toString() {
        return mDeviceName;
    }

}
