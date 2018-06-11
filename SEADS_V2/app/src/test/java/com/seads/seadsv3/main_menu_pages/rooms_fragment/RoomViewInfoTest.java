package com.seads.seadsv3.main_menu_pages.rooms_fragment;

import org.junit.Test;

import static junit.framework.Assert.fail;

/**
 * This test ensures RoomViewInfo is not modified in a way that will prevent current recycler
 * views from using
 */
public class RoomViewInfoTest {
    /**
     * Test to make sure not of our fields end up null as these will be used in the recycler view
     */
    @Test
    public void roomViewInfoTestNulls() {
        RoomViewInfo roomDeviceViewInfo = new RoomViewInfo("",2);

        if (roomDeviceViewInfo.getDevicesInRoom()!=2) {
            fail();
        }
        if (roomDeviceViewInfo.getTitle()==null) {
            fail();
        }

        if (roomDeviceViewInfo.getDevicesInRoom()!=2) {
            fail();
        }
        if (roomDeviceViewInfo.getTitle()==null) {
            fail();
        }
    }

}