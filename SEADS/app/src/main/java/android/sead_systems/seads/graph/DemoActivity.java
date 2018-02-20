package android.sead_systems.seads.graph;

import android.content.Intent;
import android.os.Bundle;
import android.sead_systems.seads.energy_cost.EnergyCostActivity;
import android.sead_systems.seads.device_list.DeviceListActivity;
import android.sead_systems.seads.R;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

/*
    This is the main activity that inflates the 5 different fragments containing graphs.
    Inside also contain the tab manager organizing each fragment as swipe-able tabs by invoking
    PageAdapter class.

 */
public class DemoActivity extends AppCompatActivity {
    private boolean firstRunForBottomBar = true;

    private String mCurrentRoomName;
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Live"));
        tabLayout.addTab(tabLayout.newTab().setText("Past"));
        tabLayout.addTab(tabLayout.newTab().setText("Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Devices"));
        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        if (getIntent().getStringExtra("ROOM_NAME") != null) {
            mCurrentRoomName = getIntent().getStringExtra("ROOM_NAME");
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_main_menu);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        if (bottomBar != null) {
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if(!firstRunForBottomBar){
                        if (tabId == R.id.tab_left) {

                        } else if (tabId == R.id.tab_center) {
                            Intent intent = new Intent(getApplicationContext(),
                                    EnergyCostActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (tabId == R.id.tab_right) {

                        }
                    } else {
                        firstRunForBottomBar = false;
                    }
                }
            });
        }

        if (bottomBar != null) {
            bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
                @Override
                public void onTabReSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_left) {

                    } else if (tabId == R.id.tab_center) {
                        Intent intent = new Intent(getApplicationContext(),
                                EnergyCostActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (tabId == R.id.tab_right) {

                    }
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        bottomBar.selectTabAtPosition(0);
    }

    /** jump to the listview activity **/
    public void toList(View v){
        Intent intent = new Intent(DemoActivity.this, DeviceListActivity.class);
        Bundle extras = new Bundle();
        extras.putString("Room", mCurrentRoomName);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}