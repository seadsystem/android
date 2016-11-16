package android.sead_systems.seads;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

/**
 * User settings activity - allow user to add/delete rooms and devices as well as log out.
 * @author Talal Abou Haiba
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);

//        Preference p = findPreference("create_room");
//        p.setEnabled(false);
//        p.setSelectable(false);

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        String key = preference.getKey();

        Intent intent;

        switch(key) {

            case "create_room":
                intent = new Intent(this, AddDevice.class);
                startActivity(intent);
                finish();

                break;

            case "delete_room":
                Toast.makeText(SettingsActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();

                break;

            case "add_device":
                intent = new Intent(this, AddDevice.class);
                startActivity(intent);
                finish();

                break;

            case "delete_device":
                Toast.makeText(SettingsActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();

                break;

            case "log_out":
                getSharedPreferences(getString(R.string.shared_preferences), MODE_PRIVATE).edit().
                        putBoolean(getString(R.string.preference_logged_in), false).apply();
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;

            case "clear_data":
                getSharedPreferences(getString(R.string.shared_preferences), MODE_PRIVATE).edit().
                        putBoolean(getString(R.string.preference_logged_in), false).apply();
                getSharedPreferences(getString(R.string.shared_preferences), MODE_PRIVATE).edit().
                        putBoolean(getString(R.string.preference_first_time), true).apply();
                intent = new Intent(this, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;
        }

        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

}


