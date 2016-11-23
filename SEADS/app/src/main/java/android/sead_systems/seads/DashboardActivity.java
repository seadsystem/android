package android.sead_systems.seads;


import android.app.ProgressDialog;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
   /** GestureDetectorCompat gestureObject; */

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //begin swipe method
       /*** gestureObject = new GestureDetectorCompat(this, new LearnGesture()); **/

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        if (bottomBar != null) {
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_left) {
                        // Current Activity -> do nothing
                    } else if (tabId == R.id.tab_center) {
                        Intent intent = new Intent(getApplicationContext(),
                                CostActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    } else if (tabId == R.id.tab_right) {
                        Intent intent = new Intent(getApplicationContext(),
                                SettingsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            });
        }

        if (bottomBar != null) {
            bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
                @Override
                public void onTabReSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_left) {
                        // Current Activity -> do nothing
                    } else if (tabId == R.id.tab_center) {
                        Intent intent = new Intent(getApplicationContext(),
                                CostActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    } else if (tabId == R.id.tab_right) {
                        Intent intent = new Intent(getApplicationContext(),
                                SettingsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("On Resume!");
        runUpdate();
    }

    private void runUpdate() {
        if (RoomManagerFactory.getInstance().generateListOfRooms().isEmpty()) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Updating");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
        }
        updateData();
    }

    /**
     * Makes a connection to the server and obtains recent the most recent database.
     */
    private void updateData() {

        DatabaseReference roomRef = mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("rooms").getRef();

        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean dataUpdated = false;
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (RoomManagerFactory.getInstance().getRoom(dsp.getKey()) == null) {
                        System.out.println("Updating with: " + dsp.getKey());
                        RoomObject newRoom = new RoomObject(dsp.getKey(), ((Long) dsp.child("room_image").getValue()).intValue());
                        RoomManagerFactory.getInstance().insertRoom(newRoom);
                        dataUpdated = true;
                    }
                }
                if (dataUpdated) {
                    updateAdapter();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void updateAdapter() {
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
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    //enables swipe gesture to get to listview
    /***
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

    } **/


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
