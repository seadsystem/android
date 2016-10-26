package android.sead_systems.seads.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Created by talal.abouhaiba on 10/19/2016. */

public class DeviceListManager {

    private HashMap<String, DeviceObject> mDeviceObjects;

    DeviceListManager() {
        mDeviceObjects = new HashMap<>();

        // FIXME: Debug code, only for demo!
        insertDevice(new DeviceObject("Fridge", true, 23));
        insertDevice(new DeviceObject("Laptop", false, 23));
        insertDevice(new DeviceObject("Tesla", true, 23));
        insertDevice(new DeviceObject("Microwave", true, 23));
    }

    public void insertDevice(DeviceObject newObject) {
        mDeviceObjects.put(newObject.toString(), newObject);
    }

    public long generateCurrentPowerUsage() {
        long totalConsumption = 0;
        for (Object value : mDeviceObjects.values()) {
            totalConsumption += ((DeviceObject)value).getDeviceUsage();
        }
        return totalConsumption;
    }

    public List<String> generateListOfDevices() {
        List<String> listOfDevices = new ArrayList<>();
        for (Object value : mDeviceObjects.values()) {
            listOfDevices.add(value.toString());
        }
        return listOfDevices;
    }


}
