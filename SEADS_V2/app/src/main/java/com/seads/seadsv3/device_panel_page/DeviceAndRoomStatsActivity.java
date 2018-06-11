package com.seads.seadsv3.device_panel_page;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seads.seadsv3.BaseActivityWithDrawer;
import com.seads.seadsv3.R;
import com.seads.seadsv3.SeadsAppliance;
import com.seads.seadsv3.SeadsDevice;
import com.seads.seadsv3.SeadsRoom;

/**
 * Handle the Device and room page
 */
public class DeviceAndRoomStatsActivity extends BaseActivityWithDrawer implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TabLayout mTabLayout;
    private String room;
    private SeadsAppliance seadsAppliance;

    /**
     * Setup the views and nav drawer
     * @param savedInstanceState Android instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_panel_main);
        room = getIntent().getExtras().getString("Device");
        seadsAppliance = getIntent().getExtras().getParcelable("seads");
        setupViewPager();
        setupNavigationDrawer();
        setupToolBar();


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

    }

    /**
     * TODO: Consider removing the view pager
     */
    private void setupViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.pager_rooms_and_devices);
        Device_pager_adapter device_pager_adapter = new Device_pager_adapter(getSupportFragmentManager(),
                DeviceAndRoomStatsActivity.this, this.room, this.seadsAppliance);
        mViewPager.setAdapter(device_pager_adapter);

    }

    /**
     * Setup the toolbar
     */
    private void setupToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_rooms_and_devices);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    /**
     * Handle actionbar buttons
     * @param item item pressed
     * @return let os handle bool
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * End the activity on back press
     */
    @Override
    public void onBackPressed(){
        Log.d("DEVICE_STATS:", "Back pressed");
//        Intent intent = new Intent(this, MainMenuActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        startActivity(intent);
        finish();
    }
}
