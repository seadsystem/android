package android.sead_systems.seads;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If first time launch -> WelcomeActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

        // If not logged in -> LoginActivity
        // If logged in -> DashboardActivity
    }
}
