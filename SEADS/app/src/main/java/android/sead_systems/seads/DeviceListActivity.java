package android.sead_systems.seads;

import android.content.Intent;
import android.sead_systems.seads.devices.DeviceListManager;
import android.sead_systems.seads.devices.DeviceObject;
import android.sead_systems.seads.rooms.RoomListManager;
import android.sead_systems.seads.rooms.RoomManagerFactory;
import android.sead_systems.seads.rooms.RoomObject;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class DeviceListActivity extends AppCompatActivity {


    public RoomListManager rooms = new RoomListManager();
    public DeviceListManager devList = new DeviceListManager();
    public DeviceObject temp;
    //first used to determine if a device has been added or not
    public boolean first;

    public void onClick(View target){
        Intent intent = new Intent(DeviceListActivity.this, AddDevice.class);
        first = true;
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
            Intent intent = getIntent();

        /***String name = "garage";
        RoomObject garage = new RoomObject(name);
        rooms.insertRoom(garage);
***/

        //add to dynamically change list
           /** if(intent.getStringExtra("New") != null) {
                String dev = intent.getStringExtra("New");
                Toast.makeText(DeviceListActivity.this, dev, Toast.LENGTH_LONG).show();
                temp = new DeviceObject(dev, false, 0);
             //   RoomManagerFactory.getInstance().getRoom("garage").
                devList.insertDevice(temp);
            }  ***/



        populateListView();
  /*      button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()){
            public void onClick(View v) {

            }
        }*/
    }

    private void populateListView() {
        //can adapt this list to be only which devices the user wants to see
        //devList = new DeviceListManager();

        //
       /** boolean [] stat = devList.generateListOfStatuses();**/

        boolean[] stat = { true,false,false,true,true,false,false,false,true,false};
        String[] devices = {"Fridge", "Heater", "Electric Car", "Air Conditioning", "TV", "Computer", "Washer", "Drier", "Laptop Charging", "Microwave"};


        Integer[] light = new Integer[stat.length];

        //if(light[0] == null)  Toast.makeText(DeviceListActivity.this,"null", Toast.LENGTH_LONG).show();
        for (int i = 0 ; i < stat.length; i ++){
            if(stat[i]){
                light[i] = R.drawable.device_on;
            }else{
                light[i] = R.drawable.device_off;
            }
        }

        String [] deviceStrings = new String[stat.length];
        int index = 0;
        deviceStrings = devices;
        for(Object value : devList.generateListOfDevices()) {
            if(devList.generateListOfDevices()!=null) {
                deviceStrings[index] = (String) value;
                index++;
            }
        }

       // Toast.makeText(DeviceListActivity.this,, Toast.LENGTH_LONG).show();
        Adapter adapter = new Adapter(DeviceListActivity.this,deviceStrings, light);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

        ((TextView) findViewById(R.id.currentUsageTextbox)).setText(String.valueOf(
               devList.generateCurrentPowerUsage()));


    }

    //This function will be used to run an individual fragment based on which item was clicked
    private void registerOnClick() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //put method to run graph fragment here
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String clicked = textView.getText().toString();
                //show which list item the user has clicked
                Toast.makeText(DeviceListActivity.this, clicked, Toast.LENGTH_SHORT).show();


            }
        });
    }

}
