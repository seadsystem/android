package com.seads.seadsv3.main_menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BubbleChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.seads.seadsv3.AboutActivity;
import com.seads.seadsv3.BaseActivityWithDrawer;
import com.seads.seadsv3.R;
import com.seads.seadsv3.SeadsAppliance;
import com.seads.seadsv3.SeadsDevice;
import com.seads.seadsv3.SeadsRoom;
import com.seads.seadsv3.http.WebInterface;
import com.seads.seadsv3.http.WebInterfacer;
import com.seads.seadsv3.main_menu_pages.PagerAdapterSEADS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Main activity which obtains and displays a list of rooms.

 */

public class MainMenuActivity extends BaseActivityWithDrawer implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private TabLayout mTabLayout;
    private SeadsDevice seadsDevice;


    /**
     * Setup views and references to firebase
     * @param savedInstanceState Android instance state
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Log.d("FirebaseUser", firebaseUser.getDisplayName());
        DatabaseReference reference = mDatabase.getDatabase().getReference();
        Query query = reference.child("users").child(mAuth.getUid()).child("devices");
        Log.d("Firebase", "querying firebase");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot issue : dataSnapshot.getChildren()){
                        try {
                            if(
                                    !(issue.child("name").getValue() == null) &&
                                    !issue.child("name").getValue().toString().toLowerCase().contains("test") &&
                                    !issue.child("name").getValue().toString().toLowerCase().contains("fake")
                                    ) {
                                Log.d("Firebase", issue.toString());
                                seadsDevice = new SeadsDevice(issue);
                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("seads", seadsDevice);
                setupViewPager();
                setupToolBar();
                setupNavigationDrawer();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    /**
     * TODO determine if this is overwritten by mistake as setupNavigationDrawer() is already called
     * Appears to be okay to leave this in the code...
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavigationDrawer();
    }

    /**
     * Configure the ViewPager that handles the tabular layout
     */
    private void setupViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.pager_rooms_and_devices);
        mViewPager.setOffscreenPageLimit(2);
        PagerAdapterSEADS pagerAdapterSEADS = new PagerAdapterSEADS(getSupportFragmentManager(),
                MainMenuActivity.this, seadsDevice);
        mViewPager.setAdapter(pagerAdapterSEADS);

        mTabLayout = (TabLayout) findViewById(R.id.tabs_main_menu);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int tabIndex = 0; tabIndex < mTabLayout.getTabCount(); tabIndex++) {
//            tabLayout.getTabAt(tabIndex).setIcon(R.drawable.rounded_button);
            mTabLayout.getTabAt(tabIndex).setCustomView(pagerAdapterSEADS.getTabView(tabIndex));
        }
        mTabLayout.setOnTabSelectedListener(this);
    }

    /**
     * Setup the action bar at the top of the activity
     */
    private void setupToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    /**
     * Overwritten for future use(unknown)
     */
    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * TODO: remove the overflow menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    /**
     * TODO: remove the overflow menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * When a tab is selected update the navigation view
     * @param tab Given tab in tablayout
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Fragment mFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int position = tab.getPosition();
        // Set the viewpager to the correct position
        mViewPager.setCurrentItem(position);
        // Change selector on the nav drawer
        setNavigationViewToPage(position);
    }

    /**
     * Unused, mandatory override if we need onTabSelected
     * @param tab
     */
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    /**
     * Unused, mandatory override if we need onTabSelected
     * @param tab
     */
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * Listen for any attempt to change the page view when another activity finishes
     * In the event of an event to start the about activity, the about activity will be started
     * When an activity is cancelled, the NavigationView's selection is reset.
     * @param requestCode request code used by finishing activity
     * @param resultCode whether the activity finished successfully or was cancelled
     * @param data data from finishing activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MainMenuActivity","In onActivityResult");
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                int resultForPageIndex = data.getIntExtra(requestDataKey,EnumNavBarNames.DEVICES.getIndex());
                Log.d("MainMenuActivity", "result:" + resultForPageIndex);
                if (mViewPager != null) {
                    if (resultForPageIndex == ABOUT_CODE) {
                        // Launch about
                        Intent intent = new Intent(this, AboutActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(intent, REQUEST_CODE);
                    } else {
                        // Change the page
                        mViewPager.setCurrentItem(resultForPageIndex);
                        setNavigationViewToPage(resultForPageIndex);
                    }
                }
            } else {
                // Reset navigation view
                Log.d("MainMenuActivity", "result cancelled");
                setNavigationViewToPage(mViewPager.getCurrentItem());
            }
        }
    }



    /**
     * Handle changing pages
     * This function is written without a switch statement as java requires constants to be used
     * within switch cases.
     * @param page page to switch to
     */
    private void setNavigationViewToPage(int page) {
        if (page == EnumNavBarNames.DEVICES.getIndex()){
            mNavigationView.setCheckedItem(R.id.nav_device);
        }
        /*else if (page == EnumNavBarNames.ROOMS.getIndex()) {
            mNavigationView.setCheckedItem(R.id.nav_rooms);
        }
         */
        else if (page == EnumNavBarNames.OVERVIEW.getIndex()) {
            mNavigationView.setCheckedItem(R.id.nav_overview);
        } else if (page == EnumNavBarNames.AWARDS.getIndex()) {
            mNavigationView.setCheckedItem(R.id.nav_awards);
        } else if (page == EnumNavBarNames.SETTINGS.getIndex()) {
            mNavigationView.setCheckedItem(R.id.nav_settings);
        }
    }

}
