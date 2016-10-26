package android.sead_systems.seads.devices;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/** Created by talal.abouhaiba on 10/25/2016. */

public class DeviceObjectTest {

    private DeviceObject mDevice = null;

    private String mDeviceName = "testDevice";
    private boolean mDeviceStatus = true;
    private double mDeviceUsage = 45.34;

    @Before
    public void initialize() {
        mDevice = new DeviceObject(mDeviceName, mDeviceStatus, mDeviceUsage);
    }

    @Test
    public void testCreateObject() {
        Assert.assertNotNull(mDevice);
    }

    @Test
    public void testCreateObjectAndVerifyInformation() {
        Assert.assertEquals(mDevice.toString(), mDeviceName);
        Assert.assertEquals(mDevice.getDeviceStatus(), mDeviceStatus);
        Assert.assertEquals(mDevice.getDeviceUsage(), mDeviceUsage);
    }

    @Test
    public void testUpdateDeviceStatus() {
        Assert.assertTrue(mDevice.getDeviceStatus());

        mDevice.updateDeviceStatus(false);
        Assert.assertFalse(mDevice.getDeviceStatus());

        mDevice.updateDeviceStatus(true);
        Assert.assertTrue(mDevice.getDeviceStatus());
    }

    @Test
    public void testUpdateDeviceUsage() {
        double updatedUsage = 32.584;

        Assert.assertEquals(mDevice.getDeviceUsage(), mDeviceUsage);

        mDevice.updateDeviceUsage(updatedUsage);
        Assert.assertEquals(mDevice.getDeviceUsage(), updatedUsage);
    }

}
