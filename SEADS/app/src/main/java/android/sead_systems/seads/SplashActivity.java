package android.sead_systems.seads;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO call this to move to next activity with proper .class
        Intent intent = new Intent(this, TemperatureActivity.class);
        startActivity(intent);
        finish();
    }
}
