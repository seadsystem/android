package com.seads.seadsv2.main_menu_pages.settings_fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.seads.seadsv2.R;

/**
 * Created by jungk on 3/2/2018.
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.fragment_settings_main_menu);
    }
}
