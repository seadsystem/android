package android.sead_systems.seads;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.sead_systems.seads.rooms.RoomManagerFactory;
import android.sead_systems.seads.rooms.RoomObject;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * User settings activity - allow user to add/delete rooms and devices as well as log out.
 * @author Talal Abou Haiba
 */

public class SettingsActivity extends PreferenceActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        String key = preference.getKey();

        Intent intent;

        switch(key) {

            case "create_room":
                final EditText input = new EditText(this);

                new AlertDialog.Builder(this)
                        .setTitle("Create Room")
                        .setMessage("Please enter a room name")
                        .setView(input)
                        .setCancelable(true)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String roomName = input.getText().toString();
                                mDatabase.child("users").child(mAuth.getCurrentUser().getUid())
                                        .child("rooms").child(roomName).child("room_image").setValue(R.mipmap.bathroom);
                                Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        })
                        .show();

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
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;

            case "clear_data":
                FirebaseAuth.getInstance().signOut();
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


