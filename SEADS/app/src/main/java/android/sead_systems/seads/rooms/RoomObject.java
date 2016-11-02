package android.sead_systems.seads.rooms;

import android.sead_systems.seads.devices.DeviceListManager;

/**
 * A container for the attributes of a single room.
 * @author Talal Abou Haiba
 */

public class RoomObject {

    private String mRoomName;

    private DeviceListManager mDeviceListManager;

    public RoomObject(String roomName) {
        mDeviceListManager = new DeviceListManager();
        mRoomName = roomName;
    }

    public DeviceListManager manageDevices() {
        return mDeviceListManager;
    }

    @Override
    public String toString() {
        return mRoomName;
    }

}
