package android.sead_systems.seads;

import android.content.Intent;
import android.os.Bundle;
import android.sead_systems.seads.devices.DeviceObject;
import android.sead_systems.seads.rooms.RoomListManager;
import android.sead_systems.seads.rooms.RoomManagerFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by camwh on 11/21/2016.
 */


public class DeviceInfo extends AppCompatActivity {

    public String Watts = " Watts.";
    public String On = "Device is on.";
    public String Off = "Device is off";
    public RoomListManager rooms = RoomManagerFactory.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String dev = bundle.getString("device");
        String room = bundle.getString("room");
        DeviceObject device = rooms.getRoom(room).manageDevices().getDevice(dev);

        /** adds the given device name **/
        TextView name = (TextView) findViewById(R.id.device_name);
        name.setText(dev);

        /** adds the power usage in Watts **/
        TextView watts = (TextView) findViewById(R.id.power_usage);
        String doubl = String.valueOf(device.getDeviceUsage());
        String concat = doubl + Watts;
        watts.setText(concat );

        /** adds the status of the device **/
        TextView status = (TextView) findViewById(R.id.status);
        if(device.getDeviceStatus()){
            status.setText(On);
        }else{
            status.setText(Off);
        }
    }
}
