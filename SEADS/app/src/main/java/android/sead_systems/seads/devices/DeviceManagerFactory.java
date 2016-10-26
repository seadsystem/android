package android.sead_systems.seads.devices;

/**
 * DeviceManagerFactory is a singleton which contains an instance of {@link DeviceListManager}
 * @author Talal Abou Haiba
 */

public class DeviceManagerFactory {

    /**
     * Singleton, hidden constructor.
     */
    private DeviceManagerFactory() {}

    private static DeviceListManager INSTANCE;

    /**
     * Lazily instantiates an instance of the DeviceListManager.
     * @return an instance of {@link DeviceListManager}
     */
    public static DeviceListManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DeviceListManager();
        }
        return INSTANCE;
    }

}
