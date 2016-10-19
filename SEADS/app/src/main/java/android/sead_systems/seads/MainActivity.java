package android.sead_systems.seads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get rid of title replace with seads logo
        setTitle("");
       // getSupportActionBar().setIcon(R.drawable.logo);
        populateListView();
        registerOnClick();

    }


    private void populateListView() {
        //can adapt this list to be only which devices the user wants to see
        String[] devices = {"Fridge", "Heater", "Electric Car", "Air Conditioning", "TV", "Computer", "Washer", "Drier", "Laptop Charging", "Microwave"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.devicelist, devices);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        //CustomList will be used when we put pictures with the text on the list, will have to redo adapter

        /*CustomList adapter1 = new
        CustomList adapter1 = new CustomList(MainActivity.this,devices,R.drawable.test);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter); */
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
                Toast.makeText(MainActivity.this, clicked, Toast.LENGTH_SHORT).show();


            }
        });
    }
}
