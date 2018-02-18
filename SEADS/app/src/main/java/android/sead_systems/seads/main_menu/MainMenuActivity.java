package android.sead_systems.seads.main_menu;

import android.os.Bundle;
import android.sead_systems.seads.R;
import android.sead_systems.seads.http.WebInterface;
import android.sead_systems.seads.page_management_main_menu.PagerAdapterSEADS;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Main activity which obtains and displays a list of rooms.

 */

public class MainMenuActivity extends AppCompatActivity implements WebInterface, NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
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
    private void setupViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapterSEADS pagerAdapterSEADS = new PagerAdapterSEADS(getSupportFragmentManager(),
                MainMenuActivity.this);
        viewPager.setAdapter(pagerAdapterSEADS);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(viewPager);
        for (int tabIndex = 0; tabIndex < mTabLayout.getTabCount(); tabIndex++) {
//            tabLayout.getTabAt(tabIndex).setIcon(R.drawable.rounded_button);
            mTabLayout.getTabAt(tabIndex).setCustomView(pagerAdapterSEADS.getTabView(tabIndex));
        }



    }
    private void setupToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setupNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id) {
            case R.id.nav_device:
//                TabLayout.Tab tab = mTabLayout.getTabAt(someIndex);
//                tab.select();
                Log.d("MainMenuActivity", "nav_device!");
                break;
            case R.id.nav_rooms:
                Log.d("MainMenuActivity", "nav_rooms!");
                break;
            case R.id.nav_overview:
                Log.d("MainMenuActivity", "nav_overview!");
                break;
            case R.id.nav_setup:
                Log.d("MainMenuActivity", "nav_setup!");
                break;
            case R.id.nav_about:
                Log.d("MainMenuActivity", "nav_about!");
                break;
            case R.id.nav_settings:
                Log.d("MainMenuActivity", "nav_settings!");
                break;
            default:
                Log.d("MainMenuActivity", "error in onNavigationItemSelected!");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
