package android.sead_systems.seads;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddDevice extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        //onButtonPressed();

    }

    public void onButtonPressed(View view){

        EditText dev = (EditText)findViewById(R.id.new_device);
        EditText room = (EditText)findViewById(R.id.new_room);
        String devName = dev.getText().toString();
        String roomName = room.getText().toString();

        //Toast.makeText(AddDevice.this, devName, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AddDevice.this, DeviceListActivity.class );
        Bundle extras = new Bundle();
        extras.putString("New", devName);
        extras.putString("Room", roomName);
        intent.putExtras(extras);
       // intent.putExtra("New", devName);
        //intent.put
        startActivity(intent);
        finish();

    }
}
