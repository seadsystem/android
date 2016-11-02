package android.sead_systems.seads.rooms;

/**
 * RoomManagerFactory is a singleton which contains an instance of {@link RoomListManager}
 * @author Talal Abou Haiba
 */

public class RoomManagerFactory {

    /**
     * Singleton, hidden constructor.
     */
    private RoomManagerFactory() {}

    private static RoomListManager INSTANCE;

    /**
     * Lazily instantiates an instance of the DeviceListManager.
     * @return an instance of {@link RoomListManager}
     */
    public static RoomListManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoomListManager();
        }
        return INSTANCE;
    }

}
