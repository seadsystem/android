package android.sead_systems.seads;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sead_systems.seads.graph.DemoActivity;
import android.sead_systems.seads.rooms.RoomManagerFactory;
import android.sead_systems.seads.rooms.RoomObject;
import android.support.annotation.IdRes;
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

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

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

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        if (bottomBar != null) {
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_left) {

                    } else if (tabId == R.id.tab_center) {
                        Intent intent = new Intent(getApplicationContext(),
                                CostActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else if (tabId == R.id.tab_right) {
                        Intent intent = new Intent(getApplicationContext(),
                                AddDevice.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }
            });
        }

        if (bottomBar != null) {
            bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
                @Override
                public void onTabReSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_left) {

                    } else if (tabId == R.id.tab_center) {
                        Intent intent = new Intent(getApplicationContext(),
                                CostActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else if (tabId == R.id.tab_right) {
                        Intent intent = new Intent(getApplicationContext(),
                                AddDevice.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                }
            });
        }

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
        private final List<RoomObject> mItems = RoomManagerFactory.getInstance().generateListOfRoomObjects();
        private final LayoutInflater mInflater;

        public MyAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public RoomObject getItem(int i) {
            return mItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return mItems.get(i).getImageId();
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

            RoomObject item = getItem(i);

            picture.setImageResource(item.getImageId());
            name.setText(item.toString());

            return v;
        }

    }
}
