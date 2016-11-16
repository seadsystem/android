package android.sead_systems.seads.rooms;

import android.sead_systems.seads.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * RoomListManager handles the storage of multiple {@link RoomObject} instances.
 * @author Talal Abou Haiba
 */

public class RoomListManager {

    private HashMap<String, RoomObject> mRoomObjects;

    public RoomListManager() {
        mRoomObjects = new HashMap<>();
        insertRoom(new RoomObject("Home", R.mipmap.bedroom1));
    }

    /**
     * Allows the insertion of a new {@link RoomObject}.
     * @param newRoom which may not be null or named similarly to an already existing room.
     */
    public synchronized void insertRoom(RoomObject newRoom) {
        if (newRoom != null && !mRoomObjects.containsKey(newRoom.toString())) {
            mRoomObjects.put(newRoom.toString(), newRoom);
        } else {
            throw new IllegalArgumentException("Cannot insert a null or duplicate room!");
        }
    }

    /**
     * Retrieves a {@link RoomObject} using the device name
     * @param roomName to be retrieved
     * @return the {@link RoomObject} associated with the deviceName
     */
    public synchronized RoomObject getRoom(String roomName) {
        return mRoomObjects.get(roomName);
    }

    /**
     * Iterates over the current set of rooms and sums up the total power usage as a double.
     * @return a double containing the current power usage, or zero if there are no rooms.
     */
    public double generateTotalPowerUsage() {
        double totalConsumption = 0;
        for (Object value : mRoomObjects.values()) {
            totalConsumption += ((RoomObject)value).manageDevices().generateCurrentPowerUsage();
        }
        return totalConsumption;
    }

    public List<String> generateListOfRooms() {
        return new ArrayList<>(mRoomObjects.keySet());
    }

    public List<RoomObject> generateListOfRoomObjects() {
        return new ArrayList<>(mRoomObjects.values());
    }

}
