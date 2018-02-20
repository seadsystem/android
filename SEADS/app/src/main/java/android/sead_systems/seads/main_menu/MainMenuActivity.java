package android.sead_systems.seads.main_menu;

import android.os.Bundle;
import android.sead_systems.seads.BaseActivityWithDrawer;
import android.sead_systems.seads.R;
import android.sead_systems.seads.http.WebInterface;
import android.sead_systems.seads.main_menu_pages.PagerAdapterSEADS;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
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

//        WebInterfacer test = new WebInterfacer(this);
//        test.getJSONObject(1477395900,1477395910,"energy",1,"Panel3", "P");
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



    @Override
    public void onJSONRetrieved(JSONObject result) {
        try {
            JSONArray data = result.getJSONArray("data");
            JSONObject index0 = data.getJSONObject(0);
            Log.d("DashboardActivity","index0 time: "+index0.getString("time"));
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        mViewPager.setCurrentItem(position);
        // Change selector on the nav drawer
        if (position == EnumNavBarNames.DEVICES.getIndex()){
            mNavigationView.setCheckedItem(R.id.nav_device);
        } else if (position == EnumNavBarNames.ROOMS.getIndex()) {
            mNavigationView.setCheckedItem(R.id.nav_rooms);
        } else if (position == EnumNavBarNames.OVERVIEW.getIndex()) {
            mNavigationView.setCheckedItem(R.id.nav_overview);
        }
        Log.d("MainMenu", "Tab clicked!");
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
