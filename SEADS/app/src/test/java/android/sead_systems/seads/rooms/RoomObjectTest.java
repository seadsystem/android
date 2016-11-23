package android.sead_systems.seads.rooms;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/** Created by talal.abouhaiba on 11/2/2016. */

public class RoomObjectTest {

    private RoomObject mRoom = null;
    private String mRoomName = "testRoom";

    @Before
    public void initialize() {
        mRoom = new RoomObject(mRoomName, 0);
    }

    @Test
    public void testCreateObject() {
        Assert.assertNotNull(mRoom);
    }

    @Test
    public void testCreateObjectAndVerifyName() {
        Assert.assertEquals(mRoom.toString(), mRoomName);
    }

    @Test
    public void testManageDevices() {
        Assert.assertNotNull(mRoom.manageDevices());
    }
    
}
