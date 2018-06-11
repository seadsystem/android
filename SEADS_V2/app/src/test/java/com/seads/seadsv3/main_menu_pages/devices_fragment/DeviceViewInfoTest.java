package com.seads.seadsv3.main_menu_pages.devices_fragment;

import org.junit.Test;

import static junit.framework.Assert.fail;

/**
 * This test ensures DeviceViewInfo is not modified in a way that will prevent current recycler
 * views from using
 */
public class DeviceViewInfoTest {
    /**
     * Test if header fields are working as expected
     */
    @Test
    public void deviceViewInfoHeaderTest() {
        DeviceViewInfo testRoomViewInfo = new DeviceViewInfo();
        // Default is not header
        if (testRoomViewInfo.isHeader()) {
            fail();
        }
        testRoomViewInfo = new DeviceViewInfo("Test");
        if (testRoomViewInfo.isHeader()) {
            fail();
        }
        testRoomViewInfo= new DeviceViewInfo("Test",true);
        if (!testRoomViewInfo.isHeader()) {
            fail();
        }
        testRoomViewInfo = new DeviceViewInfo("Test",false);
        if (!testRoomViewInfo.isHeader()) {
            fail();
        }
    }

    /**
     * Test to make sure not of our fields end up null as these will be used in the recycler view
     */
    @Test
    public void deviceViewInfoTestNulls() {
        DeviceViewInfo testDeviceViewInfo = new DeviceViewInfo();

        if (testDeviceViewInfo.getDeviceName()==null) {
            fail();
        }
        if (!(testDeviceViewInfo.getTitle()==null)) {
            fail();
        }

        testDeviceViewInfo = new DeviceViewInfo("x");

        if (testDeviceViewInfo.getDeviceName()==null) {
            fail();
        }
        if (!(testDeviceViewInfo.getTitle()==null)) {
            fail();
        }

        testDeviceViewInfo = new DeviceViewInfo("x",true);

        if (!(testDeviceViewInfo.getDeviceName()==null)) {
            fail();
        }
        if (testDeviceViewInfo.getTitle()==null) {
            fail();
        }
    }

}