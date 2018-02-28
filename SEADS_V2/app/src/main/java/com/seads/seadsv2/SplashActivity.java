package com.seads.seadsv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.seads.seadsv2.main_menu.MainMenuActivity;

/**
 * First activity loaded when cold-booting the app. Determines whether the user should be sent to
 * {@link WelcomeActivity}, {@link LoginActivity}, or {@link MainMenuActivity}.
 * @author Talal Abou Haiba
 */

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.shared_preferences), MODE_PRIVATE);

        boolean firstTime = sharedPreferences.getBoolean(getString(R.string.preference_first_time),
                true);
        boolean loggedIn = mAuth.getCurrentUser() != null;

        Intent intent;

        if (firstTime) {
            intent = new Intent(this, WelcomeActivity.class);
        } else if (!loggedIn) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, MainMenuActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
