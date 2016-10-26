package android.sead_systems.seads.devices;

/** Created by talal.abouhaiba on 10/25/2016. */

public class DeviceManagerFactory {

    /**
     * Singleton, hide constructor.
     */
    private DeviceManagerFactory() {}

    private static DeviceListManager INSTANCE;

    public static DeviceListManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DeviceListManager();
        }
        return INSTANCE;
    }

}
