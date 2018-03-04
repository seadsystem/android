package android.sead_systems.seads;

import android.app.Activity;
import android.content.Intent;
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
    public static int REQUEST_CODE = 2;
    public static int ABOUT_PAGE_FAKE_INDEX;
    public String requestDataKey = "nav";
    public static final int ABOUT_CODE = 99;
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
                closeDrawer = handleDrawerOnPressWithPage(EnumNavBarNames.DEVICES.getIndex());
                break;
            case R.id.nav_rooms:
                closeDrawer = handleDrawerOnPressWithPage(EnumNavBarNames.ROOMS.getIndex());
                break;
            case R.id.nav_overview:
                closeDrawer = handleDrawerOnPressWithPage(EnumNavBarNames.OVERVIEW.getIndex());
                break;
            case R.id.nav_awards:
                closeDrawer = handleDrawerOnPressWithPage(EnumNavBarNames.AWARDS.getIndex());
                break;
            case R.id.nav_settings:
                closeDrawer = handleDrawerOnPressWithPage(EnumNavBarNames.SETTINGS.getIndex());
                Log.d("MainMenuActivity", "nav_settings!");
                break;
            case R.id.nav_about:

                if (!(this instanceof MainMenuActivity)) {
                    Intent resultData = new Intent();
                    resultData.putExtra(requestDataKey, ABOUT_CODE);
                    setResult(Activity.RESULT_OK, resultData);
                    this.finish();
                    closeDrawer = false;
                } else {
                    Intent intent = new Intent(this, AboutActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, REQUEST_CODE);
                }
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

    private boolean handleDrawerOnPressWithPage(int pageIndex) {
        if (!(this instanceof MainMenuActivity)) {
            Intent resultData = new Intent();
            resultData.putExtra(requestDataKey, pageIndex);
            setResult(Activity.RESULT_OK, resultData);
            Log.d("BaseActivity","nav rooms clicked and finishing!");
            this.finish();
            return false;
        } else {
            if (mViewPager != null) {
                mViewPager.setCurrentItem(pageIndex);
            }
        }
        return true;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d("Base activity","In onActivityResult");
//
//    }
}
