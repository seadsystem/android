package android.sead_systems.seads.device_list;

import android.content.Intent;
import android.os.Bundle;
import android.sead_systems.seads.AddDeviceActivity;
import android.sead_systems.seads.R;
import android.sead_systems.seads.dashboard.DashboardActivity;
import android.sead_systems.seads.devices.DeviceObject;
import android.sead_systems.seads.rooms.RoomListManager;
import android.sead_systems.seads.rooms.RoomManagerFactory;
import android.sead_systems.seads.rooms.RoomObject;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Displays a list of devices associated with a given {@link RoomObject}
 * @author Cameron Wheeler
 */

public class DeviceListActivity extends AppCompatActivity {

/** necessary global variables **/
    public RoomListManager rooms = RoomManagerFactory.getInstance();
    public List<String> devList;
    public DeviceObject temp;
    public RoomObject curr;

    /**Run Add Device Activity**/
    public void onClick(View target){
        Intent intent = new Intent(DeviceListActivity.this, AddDeviceActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
            Intent intent = getIntent();

        /** try will catch instance when list is empty**/
       try{ // FIXME: This activity should ONLY be obtaining the list of devices, not creating any
                Bundle bundle = intent.getExtras();
                String dev = bundle.getString("New");
                if (dev == null) { // FIXME: Also a hack
                    dev = "";
                }
                int imgid = bundle.getInt("Image");
                temp = new DeviceObject(dev, false, 0);
                String current_room = bundle.getString("Room");
                /**if the current room is not already in the room list insert the room**/
                if(rooms.getRoom(current_room) == null){
                    RoomObject new_room = new RoomObject(current_room, imgid);
                    rooms.insertRoom(new_room);
                    /**insert the device into the newly made room object**/
                    rooms.getRoom(current_room).manageDevices().insertDevice(temp);
                    curr = rooms.getRoom(current_room);
                }
                else {
                    if (!temp.toString().isEmpty()) { // FIXME: This is a hack - this entire activity should be redone
                        rooms.getRoom(current_room).manageDevices().insertDevice(temp);
                    }
                    curr = rooms.getRoom(current_room);
                }

           populateListView();
           /** if list is empty don't try to build it **/
            } catch(NullPointerException e){
           Toast.makeText(DeviceListActivity.this, "no items in list", Toast.LENGTH_LONG).show();
       }
        //registerOnClick();

    }

    private void populateListView() {

        boolean [] stat = curr.manageDevices().generateListOfStatuses();
        devList = curr.manageDevices().generateListOfDevices();


    /** creates an array of status lights based on the boolean array**/
        Integer[] light = new Integer[stat.length];
        for (int i = 0 ; i < stat.length; i ++){
            if(stat[i]){
                light[i] = R.drawable.device_on;
            }else{
                light[i] = R.drawable.device_off;
            }
        }

        String [] deviceStrings = new String[stat.length];

        int index = 0;
        /** converts a List to an array of strings **/
        for(Object value : devList) {
                deviceStrings[index] = (String) value;
                index++;
        }

        DeviceListAdapter adapter = new DeviceListAdapter(DeviceListActivity.this,deviceStrings, light);
        ListView list = (ListView) findViewById(R.id.listView);
        if (list != null) {
            list.setAdapter(adapter);
        }

       /** ((TextView) findViewById(R.id.currentUsageTextbox)).setText(String.valueOf(
               devList.generateCurrentPowerUsage())); **/


    }

    //This function will be used to run an individual fragment based on which item was clicked
    //register onClick not working

    /**private void registerOnClick() {

        final ListView mylist = (ListView) findViewById(R.id.listView);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                LinearLayout par = (LinearLayout) viewClicked;
                Toast.makeText(DeviceListActivity.this, mylist.getCount(), Toast.LENGTH_SHORT).show();
                    ListView textView = (ListView) par.findViewById(R.id.listView);
                    Object clicked1 = (textView.getItemAtPosition(position));
                    String clicked = clicked1.toString();
                    Toast.makeText(DeviceListActivity.this, clicked, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DeviceListActivity.this, DeviceInfo.class);
                    Bundle extras = new Bundle();
                    extras.putString("room", curr.toString());
                    extras.putString("device", clicked);
                    startActivity(intent);

            }
        });
    } **/

    /** go back to dashboard **/
    public void goHome(View v){
        Intent intent = new Intent(DeviceListActivity.this, DashboardActivity.class);
        startActivity(intent);
    }


}
