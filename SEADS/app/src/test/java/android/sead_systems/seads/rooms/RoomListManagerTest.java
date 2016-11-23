package android.sead_systems.seads.rooms;

import android.sead_systems.seads.devices.DeviceObject;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/** Created by talal.abouhaiba on 11/2/2016. */

public class RoomListManagerTest {

    private RoomListManager mRoomListManager = null;

    @Before
    public void initialize() {
        mRoomListManager = new RoomListManager();
    }

    @Test
    public void testInitialize() {
        Assert.assertNotNull(mRoomListManager);
    }

    @Test
    public void testInsertAndRetrieveRoom() {
        RoomObject testRoom = new RoomObject("testRoom", 0);
        mRoomListManager.insertRoom(testRoom);

        RoomObject returnedRoom = mRoomListManager.getRoom("testRoom");
        Assert.assertEquals(testRoom, returnedRoom);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInsertNullRoom() {
        mRoomListManager.insertRoom(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInsertRoomMultipleTimes() {
        RoomObject testRoom = new RoomObject("testRoom", 0);
        mRoomListManager.insertRoom(testRoom);
        mRoomListManager.insertRoom(testRoom);
    }

    @Test
    public void testGenerateTotalUsage_EmptyList() {
        double currentUsage = 0;
        double actualUsage = mRoomListManager.generateTotalPowerUsage();

        Assert.assertEquals(currentUsage, actualUsage);
    }

    @Test
    public void testGenerateTotalUsage() {
        RoomObject testRoom1 = new RoomObject("testRoom1", 0);
        RoomObject testRoom2 = new RoomObject("testRoom2", 0);

        double currentUsage = 0;
        double device1Usage = 15;
        double device2Usage = 30;

        testRoom1.manageDevices().insertDevice(new DeviceObject("x", true, device1Usage));
        currentUsage += device1Usage;
        testRoom2.manageDevices().insertDevice(new DeviceObject("x", true, device2Usage));
        currentUsage += device2Usage;

        mRoomListManager.insertRoom(testRoom1);
        mRoomListManager.insertRoom(testRoom2);

        Assert.assertEquals(currentUsage, mRoomListManager.generateTotalPowerUsage());
    }

    @Test
    public void testGenerateRoomList_EmptyList() {
        List<String> listOfRooms = new ArrayList<>();
        List<String> actualList = mRoomListManager.generateListOfRooms();

        Assert.assertEquals(listOfRooms, actualList);
    }

    @Test
    public void testGenerateRoomList() {
        List<String> listOfRooms = new ArrayList<>();

        RoomObject testRoom1 = new RoomObject("testRoom1", 0);
        RoomObject testRoom2 = new RoomObject("testRoom2", 0);

        mRoomListManager.insertRoom(testRoom1);
        mRoomListManager.insertRoom(testRoom2);

        listOfRooms.add("testRoom2");
        listOfRooms.add("testRoom1");

        List<String> actualList = mRoomListManager.generateListOfRooms();

        Assert.assertEquals(listOfRooms, actualList);
    }

}
