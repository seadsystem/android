package com.seads.seadsv2.main_menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.seads.seadsv2.AboutActivity;
import com.seads.seadsv2.BaseActivityWithDrawer;
import com.seads.seadsv2.R;
import com.seads.seadsv2.http.WebInterface;
import com.seads.seadsv2.http.WebInterfacer;
import com.seads.seadsv2.main_menu_pages.PagerAdapterSEADS;

import org.json.JSONObject;


/**
 * Main activity which obtains and displays a list of rooms.

 */

public class MainMenuActivity extends BaseActivityWithDrawer implements WebInterface, NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setupViewPager();
        setupToolBar();
        setupNavigationDrawer();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        WebInterfacer test = new WebInterfacer(this);
        test.getJSONObject(1519862400,1519948800,"energy",60,"Panel3", "P");
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavigationDrawer();
    }

    private void setupViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.pager_rooms_and_devices);
        PagerAdapterSEADS pagerAdapterSEADS = new PagerAdapterSEADS(getSupportFragmentManager(),
                MainMenuActivity.this);
        mViewPager.setAdapter(pagerAdapterSEADS);

        mTabLayout = (TabLayout) findViewById(R.id.tabs_main_menu);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int tabIndex = 0; tabIndex < mTabLayout.getTabCount(); tabIndex++) {
//            tabLayout.getTabAt(tabIndex).setIcon(R.drawable.rounded_button);
            mTabLayout.getTabAt(tabIndex).setCustomView(pagerAdapterSEADS.getTabView(tabIndex));
        }
        mTabLayout.setOnTabSelectedListener(this);
    }

    private void setupToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * upon retreiving data from the server we should update the costs in the past day
     * @param result the result of the HTTP response
     */
    @Override
    public void onJSONRetrieved(JSONObject result) {
        try {
            Log.d("DashboardWebtest", result.toString());
//            JSONArray data = result.getJSONArray("data");
//            JSONObject index0 = data.getJSONObject(0);
//            Log.d("DashboardActivity",result.toString());
        } catch (Exception e) {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

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
                int resultForPageIndex = data.getIntExtra(requestDataKey,EnumNavBarNames.ROOMS.getIndex());
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
    @Override
    public void onBackPressed() {

        if (mViewPager.getCurrentItem() != 0) {
          mViewPager.setCurrentItem(0,true);
        }else{
            finish();
        }
    }
    private void setNavigationViewToPage(int page) {
        if (page == EnumNavBarNames.DEVICES.getIndex()){
            mNavigationView.setCheckedItem(R.id.nav_device);
        } else if (page == EnumNavBarNames.ROOMS.getIndex()) {
            mNavigationView.setCheckedItem(R.id.nav_rooms);
        } else if (page == EnumNavBarNames.OVERVIEW.getIndex()) {
            mNavigationView.setCheckedItem(R.id.nav_overview);
        } else if (page == EnumNavBarNames.AWARDS.getIndex()) {
            mNavigationView.setCheckedItem(R.id.nav_awards);
        } else if (page == EnumNavBarNames.SETTINGS.getIndex()) {
            mNavigationView.setCheckedItem(R.id.nav_settings);
        }
    }

}
