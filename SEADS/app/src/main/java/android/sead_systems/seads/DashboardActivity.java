package android.sead_systems.seads;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sead_systems.seads.graph.DemoActivity;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    GestureDetectorCompat gestureObject;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //begin swipe method
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

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

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){
            if (event2.getX() > event1.getX()) {
                //left to right swipe

            }else
                if(event2.getX() < event1.getX()){
                //right to left swipe
                    Intent intent = new Intent(DashboardActivity.this, DeviceListActivity.class);
                    finish();
                    startActivity(intent);
                }
            return true;
        }

    }


    private final class MyAdapter extends BaseAdapter {
        private final List<Item> mItems = new ArrayList<Item>();
        private final LayoutInflater mInflater;

        public MyAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mItems.add(new Item("Master Bedroom",       R.mipmap.bedroom1));
            mItems.add(new Item("Guest Bedroom",        R.mipmap.bedroom2));
            mItems.add(new Item("Living Room ",         R.mipmap.livingroom));
            mItems.add(new Item("Office",               R.mipmap.office));
            mItems.add(new Item("Master Bathroom",      R.mipmap.bathroom));
            mItems.add(new Item("Kitchen",              R.mipmap.kitchen));
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
