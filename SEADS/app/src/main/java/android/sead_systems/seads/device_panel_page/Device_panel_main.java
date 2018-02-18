package android.sead_systems.seads.device_panel_page;

import android.os.Bundle;
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
import android.view.MenuItem;
import android.sead_systems.seads.R;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Device_panel_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_panel_main);

        setupViewPager();
        setupToolBar();
        setupNavigationDrawer();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

    }

    private void setupViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new Device_pager_adapter(getSupportFragmentManager(),
                Device_panel_main.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id) {
            case R.id.nav_device:
                Log.d("Device_panel_main", "nav_device!");
                break;
            case R.id.nav_rooms:
                Log.d("Device_panel_main", "nav_rooms!");
                break;
            case R.id.nav_overview:
                Log.d("Device_panel_main", "nav_overview!");
                break;
            case R.id.nav_setup:
                Log.d("Device_panel_main", "nav_setup!");
                break;
            case R.id.nav_about:
                Log.d("Device_panel_main", "nav_about!");
                break;
            case R.id.nav_settings:
                Log.d("Device_panel_main", "nav_settings!");
                break;
            default:
                Log.d("Device_panel_main", "error in onNavigationItemSelected!");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
