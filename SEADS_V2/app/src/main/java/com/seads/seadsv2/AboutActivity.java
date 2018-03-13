package com.seads.seadsv2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Activity that displays information about the SEADS project to the user
 */
public class AboutActivity extends BaseActivityWithDrawer {

    /**
     * initialize the view
     * @param savedInstanceState android's instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setupNavigationDrawer();
        mNavigationView.setCheckedItem(R.id.nav_about);
        setupToolBar();
    }

    /**
     * sets up tool bars with appropriate buttons (back button)
     */
    private void setupToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    /**
     * this is over-ridden so we can return to the proper activity when the user is done
     * checking out the about section
     * @param item there's only 1 back button item
     * @return success or not, handled by OS
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
