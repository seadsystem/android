package android.sead_systems.seads;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.shared_preferences), MODE_PRIVATE);

        boolean firstTime = sharedPreferences.getBoolean(getString(R.string.preference_first_time),
                true);
        boolean loggedIn = sharedPreferences.getBoolean(getString(R.string.preference_logged_in),
                false);

        // If first time launch -> WelcomeActivity
        Intent intent;

        if (firstTime) {
            intent = new Intent(this, WelcomeActivity.class);
        } else if (!loggedIn) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, DashboardActivity.class);
        }

        startActivity(intent);
        finish();

        // If not logged in -> LoginActivity
        // If logged in -> DashboardActivity
    }
}
