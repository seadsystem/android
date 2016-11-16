package android.sead_systems.seads.rooms;

import android.sead_systems.seads.devices.DeviceListManager;

/**
 * A container for the attributes of a single room.
 * @author Talal Abou Haiba
 */

public class RoomObject {

    private String mRoomName;
    private int mImageID;

    private DeviceListManager mDeviceListManager;

    public RoomObject(String roomName, int imageId) {
        mDeviceListManager = new DeviceListManager();
        mRoomName = roomName;
        mImageID = imageId;
    }

    public DeviceListManager manageDevices() {
        return mDeviceListManager;
    }

    public int getImageId() {
        return mImageID;
    }

    @Override
    public String toString() {
        return mRoomName;
    }

}
