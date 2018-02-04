package android.sead_systems.seads;

import android.content.Intent;
import android.sead_systems.seads.device_list.DeviceListActivity;
import android.sead_systems.seads.rooms.RoomListManager;
import android.sead_systems.seads.rooms.RoomManagerFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddDeviceActivity extends AppCompatActivity {

    public RoomListManager rooms = RoomManagerFactory.getInstance();

    /** Test comment **/
    public String roomName;
    public int getRoom;
    public Spinner dropdown;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

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
        final Button left = (Button) findViewById(R.id.left);
        final Button right = (Button) findViewById(R.id.right);
        final ImageView img = (ImageView) findViewById(R.id.imageView);

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
                    left.setVisibility(View.VISIBLE);
                    right.setVisibility(View.VISIBLE);
                    img.setVisibility(View.VISIBLE);
                    getRoom = 1;
                }else{
                    new_room.setVisibility(View.INVISIBLE);
                    left.setVisibility(View.INVISIBLE);
                    right.setVisibility(View.INVISIBLE);
                    img.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**allows selection of a picture for the room**/
    public Integer[] room_pics ={
            R.mipmap.bedroom1,
            R.mipmap.bathroom,
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

        /** if add new device was selected, grab the entered string **/
        int check = 0;
        String devName = dev.getText().toString();
        if(getRoom == 1) {
             roomName = room.getText().toString();
        }

        /** if one field is left blank, do not let them continue **/
        if(devName.equals("") ) {
            Toast.makeText(AddDeviceActivity.this, "Please add a Device name", Toast.LENGTH_LONG).show();
            check = 1;
        }if(roomName.equals("")) {
            Toast.makeText(AddDeviceActivity.this, "Please add a Room name", Toast.LENGTH_LONG).show();
            check = 1;
        }else if(check == 0){
        /**send new device info to the data structure **/

        writeDeviceToDatabase(roomName, devName, room_pics[index]);
        Intent intent = new Intent(AddDeviceActivity.this, DeviceListActivity.class);
        Bundle extras = new Bundle();
        extras.putString("New", devName);
        extras.putString("Room", roomName);
        extras.putInt("Image", room_pics[index]);
        intent.putExtras(extras);
        startActivity(intent);
        finish();
        }

    }

    private void writeDeviceToDatabase(String roomName, String deviceName, int roomPicture) {
        if (getRoom == 1) {
            mDatabase.child("users").child(mAuth.getCurrentUser().getUid())
                    .child("rooms").child(roomName).child("room_image").setValue(roomPicture);
        }

        // Fixme: hardcoded - all devices inserted have usage 48 and status 0 (off)

        mDatabase.child("users").child(mAuth.getCurrentUser().getUid())
                .child("rooms").child(roomName).child("devices").child(deviceName).child("usage")
                .setValue(48);

        mDatabase.child("users").child(mAuth.getCurrentUser().getUid())
                .child("rooms").child(roomName).child("devices").child(deviceName).child("status")
                .setValue(0);
    }
}
