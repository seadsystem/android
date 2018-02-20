package android.sead_systems.seads;

import android.sead_systems.seads.main_menu.EnumNavBarNames;
import android.sead_systems.seads.main_menu.MainMenuActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

/**
 * Activities that extend BaseActivityWithDrawer should:
 *      use a DrawerLayout that includes the nav_drawer layout with the following line:
 *          <include layout="@layout/nav_drawer" />
 *      instantiate mToolbar, needed for drawer to work
 *      only instantiate mViewPager if in the MainMenuActivity
 */
public class BaseActivityWithDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawerLayout;
    protected Toolbar mToolbar;
    protected ViewPager mViewPager;
    protected NavigationView mNavigationView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_menu);
//        setupNavigationDrawer();
//    }



    protected void setupNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(mToolbar != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, mToolbar, R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_device);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean closeDrawer = true;
        switch(id) {
            case R.id.nav_device:
                if (!(this instanceof MainMenuActivity)) {
                    //TODO setResult should be called so that we can tell the MainMenu which of nav_device or nav_rooms was clicked
                    this.finish();
//                    overridePendingTransition(0,R.anim.fade_out);
                    closeDrawer = false;
                } else {
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(EnumNavBarNames.DEVICES.getIndex());
                    }
                }
                Log.d("MainMenuActivity", "nav_device!");
                break;
            case R.id.nav_rooms:
                if (mViewPager!=null) {
                    mViewPager.setCurrentItem(EnumNavBarNames.ROOMS.getIndex());
                }
                Log.d("MainMenuActivity", "nav_rooms!");
                break;
            case R.id.nav_overview:
                if (mViewPager!=null) {
                    mViewPager.setCurrentItem(EnumNavBarNames.OVERVIEW.getIndex());
                }
                Log.d("MainMenuActivity", "nav_overview!");
                break;
            case R.id.nav_awards:
                if (mViewPager!=null) {
                    mViewPager.setCurrentItem(EnumNavBarNames.AWARDS.getIndex());
                }
                Log.d("MainMenuActivity", "nav_setup!");
                break;
            case R.id.nav_settings:
                if (mViewPager!=null) {
                    mViewPager.setCurrentItem(EnumNavBarNames.SETTINGS.getIndex());
                }
                Log.d("MainMenuActivity", "nav_settings!");
                break;
            case R.id.nav_about:
                // TODO check if instance of mainmenu, if not, close, then start about activity
//                Intent intent = new Intent(this, AboutActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                if (!(this instanceof MainMenuActivity)) {
                    this.finish();
                    closeDrawer = false;
                }
                Log.d("MainMenuActivity", "nav_about!");
                break;
            default:
                Log.d("MainMenuActivity", "error in onNavigationItemSelected!");
        }
        if(closeDrawer) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
