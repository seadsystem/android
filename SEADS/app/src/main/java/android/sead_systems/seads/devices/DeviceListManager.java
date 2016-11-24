package android.sead_systems.seads.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * DeviceListManager handles the storage of multiple {@link DeviceObject} instances.
 * @author Talal Abou Haiba
 */

public class DeviceListManager {


    private HashMap<String, DeviceObject> mDeviceObjects;

    public DeviceListManager() {
        mDeviceObjects = new HashMap<>();
    }

    /**
     * Allows the insertion of a new {@link DeviceObject}.
     * @param newDevice which may not be null or named similarly to an already existing device.
     */
    public synchronized void insertDevice(DeviceObject newDevice) {
        if (newDevice != null && !mDeviceObjects.containsKey(newDevice.toString())) {
            mDeviceObjects.put(newDevice.toString(), newDevice);
        } else if (newDevice != null) {
            mDeviceObjects.get(newDevice.toString()).updateDeviceStatus(newDevice.getDeviceStatus());
            mDeviceObjects.get(newDevice.toString()).updateDeviceUsage(newDevice.getDeviceUsage());
        } else {
            throw new IllegalArgumentException("Cannot insert a null device!");
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
    public boolean[] generateListOfStatuses() {
        int counter = mDeviceObjects.size();
        Iterator it = mDeviceObjects.entrySet().iterator();

        boolean[] Statuses = new boolean[counter];
        List<String> Dev = generateListOfDevices();
        for(int i = 0; i < counter; i++){
            String curr = Dev.get(i);
            Map.Entry device = (Map.Entry)it.next();
            if(mDeviceObjects.get(curr).getDeviceStatus()) {
                //DeviceObject curr = device.getKey();
                Statuses[i] = true;
            }else{
                Statuses[i] = false;
            }
        }
        return Statuses;
    }

}
