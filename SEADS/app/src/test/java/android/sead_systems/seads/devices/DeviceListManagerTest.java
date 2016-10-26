package android.sead_systems.seads.devices;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/** Created by talal.abouhaiba on 10/25/2016. */

public class DeviceListManagerTest {

    private DeviceListManager mDeviceListManager = null;

    @Before
    public void initialize() {
        mDeviceListManager = new DeviceListManager();
    }

    @Test
    public void testInitialize() {
        Assert.assertNotNull(mDeviceListManager);
    }

    @Test
    public void testInsertAndRetrieveDevice() {
        DeviceObject testDevice = new DeviceObject("testDevice", true, 15);
        mDeviceListManager.insertDevice(testDevice);

        DeviceObject returnedDevice = mDeviceListManager.getDevice("testDevice");
        Assert.assertEquals(testDevice, returnedDevice);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInsertNullDevice() {
        mDeviceListManager.insertDevice(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInsertDeviceMultipleTimes() {
        DeviceObject testDevice = new DeviceObject("testDevice", true, 15);
        mDeviceListManager.insertDevice(testDevice);
        mDeviceListManager.insertDevice(testDevice);
    }

    @Test
    public void testModifyExistingDevice() {
        DeviceObject testDevice = new DeviceObject("testDevice", true, 15);
        mDeviceListManager.insertDevice(testDevice);

        DeviceObject returnedDevice = mDeviceListManager.getDevice("testDevice");

        double updatedUsage = 158;

        returnedDevice.updateDeviceStatus(false);
        returnedDevice.updateDeviceUsage(updatedUsage);

        returnedDevice = mDeviceListManager.getDevice("testDevice");

        Assert.assertFalse(returnedDevice.getDeviceStatus());
        Assert.assertEquals(updatedUsage, returnedDevice.getDeviceUsage());
    }

    @Test
    public void testGenerateTotalUsage_EmptyList() {
        double currentUsage = 0;
        double actualUsage = mDeviceListManager.generateCurrentPowerUsage();

        Assert.assertEquals(currentUsage, actualUsage);
    }

    @Test
    public void testGenerateTotalUsage() {
        double currentUsage = 0;
        double device1Usage = 15;
        double device2Usage = 30;

        DeviceObject testDevice1 = new DeviceObject("testDevice1", true, device1Usage);
        mDeviceListManager.insertDevice(testDevice1);
        currentUsage += device1Usage;

        Assert.assertEquals(currentUsage, mDeviceListManager.generateCurrentPowerUsage());

        DeviceObject testDevice2 = new DeviceObject("testDevice2", true, device2Usage);
        mDeviceListManager.insertDevice(testDevice2);
        currentUsage += device2Usage;

        Assert.assertEquals(currentUsage, mDeviceListManager.generateCurrentPowerUsage());
    }

    @Test
    public void testGenerateDeviceList_EmptyList() {
        List<String> listOfDevices = new ArrayList<>();
        List<String> actualList = mDeviceListManager.generateListOfDevices();

        Assert.assertEquals(listOfDevices, actualList);
    }

    @Test
    public void testGenerateDeviceList() {
        List<String> listOfDevices = new ArrayList<>();

        DeviceObject testDevice1 = new DeviceObject("testDevice1", true, 0);
        DeviceObject testDevice2 = new DeviceObject("testDevice2", true, 0);

        mDeviceListManager.insertDevice(testDevice1);
        mDeviceListManager.insertDevice(testDevice2);

        listOfDevices.add("testDevice2");
        listOfDevices.add("testDevice1");

        List<String> actualList = mDeviceListManager.generateListOfDevices();

        Assert.assertEquals(listOfDevices, actualList);
    }

}