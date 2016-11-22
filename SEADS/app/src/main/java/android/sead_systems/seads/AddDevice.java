package android.sead_systems.seads;

import android.content.Intent;
import android.sead_systems.seads.rooms.RoomListManager;
import android.sead_systems.seads.rooms.RoomManagerFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddDevice extends AppCompatActivity {

    public RoomListManager rooms = RoomManagerFactory.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spinner dropdown = (Spinner)findViewById(R.id.room_dropdown);
        List room_list = rooms.generateListOfRooms();
        String [] room_strings = new String[room_list.size()];

        int index = 0;
        /** converts a List to an array of strings **/
        for(Object value : room_list) {
            room_strings[index] = (String) value;
            index++;
        }
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, room_strings);
            dropdown.setAdapter(adapter);
        }
        catch(NullPointerException e){
            Toast.makeText(AddDevice.this, "no items in list", Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_add_device);
        //onButtonPressed();

    }

    public void onButtonPressed(View view){

        /** take in user text from the fields **/
        EditText dev = (EditText)findViewById(R.id.new_device);
        EditText room = (EditText)findViewById(R.id.new_room);
        String devName = dev.getText().toString();
        String roomName = room.getText().toString();

    /**send new device info to the data structure **/
        Intent intent = new Intent(AddDevice.this, DeviceListActivity.class );
        Bundle extras = new Bundle();
        extras.putString("New", devName);
        extras.putString("Room", roomName);
        intent.putExtras(extras);
        startActivity(intent);
        finish();

    }
}
