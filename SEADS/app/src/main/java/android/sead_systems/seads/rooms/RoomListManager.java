package android.sead_systems.seads.rooms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * RoomListManager handles the storage of multiple {@link RoomObject} instances.
 * @author Talal Abou Haiba
 */

public class RoomListManager {

    private HashMap<String, RoomObject> mRoomObjects;
    private List<String> mRoomOrdering;

    public RoomListManager() {
        mRoomObjects = new HashMap<>();
        mRoomOrdering = new ArrayList<>();
    }

    /**
     * Allows the insertion of a new {@link RoomObject}.
     * @param newRoom which may not be null or named similarly to an already existing room.
     */
    public synchronized void insertRoom(RoomObject newRoom) {
        if (newRoom != null && !mRoomObjects.containsKey(newRoom.toString())) {
            mRoomObjects.put(newRoom.toString(), newRoom);
            mRoomOrdering.add(newRoom.toString());
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

    /**
     * Generates a list of all room names.
     * @return List of the keySet.
     */
    public List<String> generateListOfRooms() {
        return new ArrayList<>(mRoomObjects.keySet());
    }

    /**
     * Generates an ordered list (based on insertion order maintained by mRoomOrdering)
     * @return ordered list of Room object references
     */
    public List<RoomObject> generateListOfRoomObjects() {
        List<RoomObject> inOrderList = new ArrayList<>();
        for (String room : mRoomOrdering) {
            inOrderList.add(mRoomObjects.get(room));
        }
        return inOrderList;
    }

    /**
     * Clears all stored room data.
     * To be used when logging out in {@link android.sead_systems.seads.SettingsActivity}
     */
    public void clearRoomData() {
        mRoomObjects.clear();
        mRoomOrdering.clear();
    }

}
