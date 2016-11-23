package android.sead_systems.seads;

import android.content.Intent;
import android.sead_systems.seads.rooms.RoomListManager;
import android.sead_systems.seads.rooms.RoomManagerFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddDevice extends AppCompatActivity {

    public RoomListManager rooms = RoomManagerFactory.getInstance();

    public String roomName;
    public int getRoom;
    public Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        dropdown = (Spinner) findViewById(R.id.room_dropdown);
        List<String> room_list = rooms.generateListOfRooms();

        String[] room_strings = new String[room_list.size() + 1];
        //ArrayAdapter<String> adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, room_list);


        int ind = 0;
        /** converts a List to an array of strings **/
        for (Object value : room_list) {
            room_strings[ind] = (String) value;
            ind++;
        }
        room_strings[ind] = "Add new room.";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, room_strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
         roomName = dropdown.getSelectedItem().toString();
        final EditText new_room =(EditText) findViewById(R.id.new_room);

        /** if a pre-existing room is selected hide the edit text **/
        if(roomName.equals("Add new room.")){
            new_room.setVisibility(View.VISIBLE);
        }else{
            new_room.setVisibility(View.INVISIBLE);
        }
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roomName = dropdown.getSelectedItem().toString();
                if(roomName.equals("Add new room.")){
                    new_room.setVisibility(View.VISIBLE);
                    getRoom = 1;
                }else{
                    new_room.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


        // dropdown.setOnItemClickListener(new View.OnItemClickListener() {
        //  @Override

       // });
        /**
         *

        try {
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, room_strings);
            adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            dropdown.setAdapter(adapt);
        }
        catch(NullPointerException e){
            Toast.makeText(AddDevice.this, "no items in list", Toast.LENGTH_LONG).show();

        }
        setContentView(R.layout.activity_add_device);
        //onButtonPressed();  **/







    /**allows selection of a picture for the room**/
    public Integer[] room_pics ={
            R.mipmap.bathroom,
            R.mipmap.bedroom1,
            R.mipmap.bedroom2,
            R.mipmap.kitchen,
            R.mipmap.livingroom,
            R.mipmap.office,
    };
    /** parses through the array of image ids in the desired direction **/
    public int index;
    public void picture_left(View view){
        index --;
        if(index < 0){
            index = room_pics.length -1;
        }
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(room_pics[index]);

    }

    public void picture_right(View view){
        index ++;
        if(index == room_pics.length){
            index = 0;
        }
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(room_pics[index]);

    }

    public void onButtonPressed(View view){

        /** take in user text from the fields **/



        EditText dev = (EditText)findViewById(R.id.new_device);
        EditText room = (EditText)findViewById(R.id.new_room);
        int check = 0;
        String devName = dev.getText().toString();
        if(getRoom == 1) {
             roomName = room.getText().toString();
        }

        /** if one field is left blank, do not let them continue **/
        if(devName.equals("") ) {
            Toast.makeText(AddDevice.this, "Please add a Device name", Toast.LENGTH_LONG).show();
            check = 1;
        }if(roomName.equals("")) {
            Toast.makeText(AddDevice.this, "Please add a Room name", Toast.LENGTH_LONG).show();
            check = 1;
        }else if(check == 0){
        /**send new device info to the data structure **/
        Intent intent = new Intent(AddDevice.this, DeviceListActivity.class);
        Bundle extras = new Bundle();
        extras.putString("New", devName);
        extras.putString("Room", roomName);
        extras.putInt("Image", room_pics[index]);
        intent.putExtras(extras);
        startActivity(intent);
        finish();
        }

    }
}
