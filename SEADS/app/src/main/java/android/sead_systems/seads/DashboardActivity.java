package android.sead_systems.seads;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sead_systems.seads.graph.DemoActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Gets the Item's Name and displays to the user currently
                TextView tv = (TextView) view.findViewById(R.id.text);
                Toast toast = Toast.makeText(getApplicationContext(), tv.getText(),
                        Toast.LENGTH_SHORT);
                toast.show();

                // TODO Need function to take the name and populate the next activity based off
                // TODO the current item's name that is clicked.
                Intent intent = new Intent(getApplicationContext(), DemoActivity.class);
                startActivity(intent);
            }
        });

       /* // FIXME: This is DEBUG code meant only for the demo. Remove this!
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
        */
        // getSupportActionBar().setIcon(R.mipmap.logo);
        //populateListView();
        //registerOnClick();

    }
    /*
    private void populateListView() {
        //can adapt this list to be only which devices the user wants to see
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.devicelist, DeviceManagerFactory.getInstance().generateListOfDevices() );
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

        ((TextView) findViewById(R.id.currentUsageTextbox)).setText(String.valueOf(
                DeviceManagerFactory.getInstance().generateCurrentPowerUsage()));
        //CustomList will be used when we put pictures with the text on the list, will have to redo adapter

        //CustomList adapter1 = new
        //CustomList adapter1 = new CustomList(DashboardActivity.this,devices,R.drawable.test);
        //list=(ListView)findViewById(R.id.list);
        //list.setAdapter(adapter);
    }

    //This function will be used to run an individual fragment based on which item was clicked
    private void registerOnClick() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //put method to run graph fragment here
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
            }
        });
    }

    */
    private final class MyAdapter extends BaseAdapter {
        private final List<Item> mItems = new ArrayList<Item>();
        private final LayoutInflater mInflater;

        public MyAdapter(Context context) {
            mInflater = LayoutInflater.from(context);

            mItems.add(new Item("1",        R.mipmap.logo));
            mItems.add(new Item("2",        R.mipmap.logo));
            mItems.add(new Item("3",        R.mipmap.logo));
            mItems.add(new Item("4",        R.mipmap.logo));
            mItems.add(new Item("5",        R.mipmap.logo));
            mItems.add(new Item("6",        R.mipmap.logo));
            mItems.add(new Item("7",        R.mipmap.logo));
            mItems.add(new Item("8",        R.mipmap.logo));
            mItems.add(new Item("9",        R.mipmap.logo));
            mItems.add(new Item("10",       R.mipmap.logo));
            mItems.add(new Item("11",       R.mipmap.logo));
            mItems.add(new Item("12",       R.mipmap.logo));
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Item getItem(int i) {
            return mItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return mItems.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            ImageView picture;
            TextView name;

            if (v == null) {
                v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView) v.getTag(R.id.picture);
            name = (TextView) v.getTag(R.id.text);

            Item item = getItem(i);

            picture.setImageResource(item.drawableId);
            name.setText(item.name);

            return v;
        }

        private class Item {
            public final String name;
            public final int drawableId;

            Item(String name, int drawableId) {
                this.name = name;
                this.drawableId = drawableId;
            }
        }
    }
}


