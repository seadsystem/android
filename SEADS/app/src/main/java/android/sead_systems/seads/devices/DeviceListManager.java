package android.sead_systems.seads.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DeviceListManager handles the storage of multiple {@link DeviceObject} instances.
 * @author Talal Abou Haiba
 */

public class DeviceListManager {

    private HashMap<String, DeviceObject> mDeviceObjects;

    DeviceListManager() {
        mDeviceObjects = new HashMap<>();
    }

    /**
     * Allows the insertion of a new {@link DeviceObject}.
     * @param newDevice which may not be null or named similarly to an already existing device.
     */
    public synchronized void insertDevice(DeviceObject newDevice) {
        if (newDevice != null && !mDeviceObjects.containsKey(newDevice.toString())) {
            mDeviceObjects.put(newDevice.toString(), newDevice);
        } else {
            throw new IllegalArgumentException("Cannot insert a null or duplicate device!");
        }
    }

    /**
     * Retrieves a {@link DeviceObject} using the device name
     * @param deviceName to be retrieved
     * @return the {@link DeviceObject} associated with the deviceName
     */
    public synchronized DeviceObject getDevice(String deviceName) {
        return mDeviceObjects.get(deviceName);
    }

    /**
     * Iterates over the current set of devices and sums up the total power usage as a double.
     * @return a double containing the current power usage, or zero if there are no devices.
     */
    public double generateCurrentPowerUsage() {
        double totalConsumption = 0;
        for (Object value : mDeviceObjects.values()) {
            totalConsumption += ((DeviceObject)value).getDeviceUsage();
        }
        return totalConsumption;
    }

    /**
     * Generates a list of device names.
     * @return a list containing the names of all current DeviceObjects
     */
    public List<String> generateListOfDevices() {
        return new ArrayList<>(mDeviceObjects.keySet());
    }

}