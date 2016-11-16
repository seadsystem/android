package android.sead_systems.seads;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;


public class TipsActivity extends AppCompatActivity {

    private boolean firstRunForBottomBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabAtPosition(1);
        if (bottomBar != null) {
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if(!firstRunForBottomBar){
                        if (tabId == R.id.tab_left) {
                            System.out.println("Left");
                            Intent intent = new Intent(getApplicationContext(),
                                    DashboardActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        } else if (tabId == R.id.tab_center) {
                            System.out.println("Center");

                        } else if (tabId == R.id.tab_right) {
                            System.out.println("Right");

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
                        Intent intent = new Intent(getApplicationContext(),
                                DashboardActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else if (tabId == R.id.tab_center) {

                    } else if (tabId == R.id.tab_right) {

                    }
                }
            });
        }

    }

}
