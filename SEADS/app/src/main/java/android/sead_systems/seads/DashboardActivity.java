package android.sead_systems.seads;

import android.content.Intent;
import android.os.Bundle;
import android.sead_systems.seads.devices.DeviceListManager;
import android.sead_systems.seads.devices.DeviceObject;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FIXME: This is DEBUG code meant only for the demo. Remove this!
        TextView textView = (TextView) findViewById(R.id.currentUsageTextbox);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TemperatureActivity.class);
                startActivity(intent);

            }
        });


        //get rid of title replace with seads logo
        setTitle("");
        // getSupportActionBar().setIcon(R.mipmap.logo);
        populateListView();
        registerOnClick();

    }


    private void populateListView() {
        //can adapt this list to be only which devices the user wants to see
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.devicelist, DeviceListManager.getInstance().generateListOfDevices() );
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

        ((TextView) findViewById(R.id.currentUsageTextbox)).setText(String.valueOf(
                DeviceListManager.getInstance().generateCurrentPowerUsage()));
        //CustomList will be used when we put pictures with the text on the list, will have to redo adapter

        /*CustomList adapter1 = new
        CustomList adapter1 = new CustomList(DashboardActivity.this,devices,R.drawable.test);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter); */
    }

    // FIXME: This is DEBUG code!
    static int temp = 0;

    //This function will be used to run an individual fragment based on which item was clicked
    private void registerOnClick() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //put method to run graph fragment here
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                // FIXME: This is strictly debug code and should be removed after testing/demo
                DeviceListManager.getInstance().insertDevice(new DeviceObject(String.valueOf(temp++), false, 34));
                populateListView();
            }
        });
    }
}
