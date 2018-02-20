package android.sead_systems.seads.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.sead_systems.seads.LoginActivity;
import android.sead_systems.seads.R;
import android.sead_systems.seads.SettingsActivity;
import android.sead_systems.seads.devices.DeviceObject;
import android.sead_systems.seads.energy_cost.EnergyCostActivity;
import android.sead_systems.seads.graph.DemoActivity;
import android.sead_systems.seads.http.WebInterface;
import android.sead_systems.seads.http.WebInterfacer;
import android.sead_systems.seads.rooms.RoomManagerFactory;
import android.sead_systems.seads.rooms.RoomObject;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Main activity which obtains and displays a list of rooms.
 * @author Talal Abou Haiba
 * @author Chris Persons
 */

public class DashboardActivity extends AppCompatActivity implements WebInterface{

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private ProgressDialog mProgressDialog;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        setBottomBar();
        WebInterfacer test = new WebInterfacer(this);
        test.getJSONObject(1477395900,1477395910,"energy",1,"Panel3", "P");

    }

    @Override
    public void onResume() {
        super.onResume();
        bottomBar.selectTabAtPosition(0);
        runUpdate();
    }

    /**
     * Instantiates the bottom navigation bar.
     */
    private void setBottomBar() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        if (bottomBar != null) {
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    Intent intent;
                    switch(tabId) {

                        case R.id.tab_left:
                            break;

                        case R.id.tab_center:
                            intent = new Intent(getApplicationContext(), EnergyCostActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            break;

                        case R.id.tab_right:
                            intent = new Intent(getApplicationContext(), SettingsActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            break;
                    }
                }
            });
        }

        if (bottomBar != null) {
            bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
                @Override
                public void onTabReSelected(@IdRes int tabId) {
                    Intent intent;
                    switch(tabId) {

                        case R.id.tab_left:
                            break;

                        case R.id.tab_center:
                            intent = new Intent(getApplicationContext(), EnergyCostActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            break;

                        case R.id.tab_right:
                            intent = new Intent(getApplicationContext(), SettingsActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            break;
                    }
                }
            });
        }

    }

    /**
     * Populates rooms and devices from Firebase. If the list of rooms is empty, display a progress
     * dialog. Otherwise, continue with the update in the background.
     */
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

        // If there is an error obtaining the current user, sign out and return to LoginActivity
        if (mAuth.getCurrentUser() == null) {
            RoomManagerFactory.getInstance().clearRoomData();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        final DatabaseReference roomRef = mDatabase.child("users")
                .child(mAuth.getCurrentUser().getUid()).child("rooms").getRef();

        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean dataUpdated = false;
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    final String currentRoomName = dsp.getKey();
                    if (RoomManagerFactory.getInstance().getRoom(dsp.getKey()) == null) {
                        RoomObject newRoom = new RoomObject(dsp.getKey(),
                                ((Long) dsp.child("room_image").getValue()).intValue());
                        RoomManagerFactory.getInstance().insertRoom(newRoom);
                        dataUpdated = true;
                    }

                    // Insert devices into room

                    DatabaseReference deviceRef = roomRef.child(currentRoomName).child("devices").getRef();
                    deviceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println("In snapshot "  + currentRoomName);
                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                boolean newDeviceStatus = dsp.child("status").getValue().toString()
                                        .equals("1");
                                double newDeviceUsage = Double.valueOf(dsp.child("usage").getValue()
                                        .toString());
                                DeviceObject newDevice = new DeviceObject(dsp.getKey(),
                                        newDeviceStatus, newDeviceUsage);
                                RoomManagerFactory.getInstance().getRoom(currentRoomName).
                                        manageDevices().insertDevice(newDevice);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                GridView gridView = (GridView) findViewById(R.id.gridview);
                if (dataUpdated) {
                    updateAdapter();
                } else if (gridView == null || gridView.getAdapter() == null ||
                        gridView.getAdapter().isEmpty()) {
                    updateAdapter();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * Sets the {@link DashboardAdapter} using room information obtained from
     * {@link RoomManagerFactory}
     */
    private void updateAdapter() {
        GridView gridView = (GridView) findViewById(R.id.gridview);

        if (gridView != null) {
            gridView.setAdapter(new DashboardAdapter(this));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView textView = (TextView) view.findViewById(R.id.text);

                    Intent intent = new Intent(getApplicationContext(), DemoActivity.class);
                    intent.putExtra("ROOM_NAME", textView.getText());
                    startActivity(intent);
                }
            });
        }

        // Dismiss the progress dialog if it is currently active
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }

    @Override
    public void onJSONRetrieved(JSONObject result) {
        try {
            JSONArray data = result.getJSONArray("data");
            JSONObject index0 = data.getJSONObject(0);
            Log.d("DashboardActivity","index0 time: "+index0.getString("time"));
        } catch (Exception e) {

        }

    }
}
