package android.sead_systems.seads.devices;

/**
 * A container for the attributes of a single device.
 * @author Talal Abou Haiba
 */

public class DeviceObject {

    private String mDeviceName;

    private boolean mDeviceStatus;
    private double mDeviceUsage;

    public DeviceObject(String deviceName, boolean currentStatus, double usage) {
        mDeviceName = deviceName;
        mDeviceStatus = currentStatus;
        mDeviceUsage = usage;
    }

    public void updateDeviceUsage(double updatedUsage) {
        mDeviceUsage = updatedUsage;
    }

    public void updateDeviceStatus(boolean updatedStatus) {
        mDeviceStatus = updatedStatus;
    }

    public double getDeviceUsage() {
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
