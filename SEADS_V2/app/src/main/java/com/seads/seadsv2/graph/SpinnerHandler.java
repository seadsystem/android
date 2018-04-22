package com.seads.seadsv2.graph;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Home on 2/22/18.
 */
@Deprecated
public class SpinnerHandler implements AdapterView.OnItemSelectedListener {
    private final String[] selections = {};


    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Log.d("SPINNER: ", "Selected position" + pos+":"+parent.getItemAtPosition(pos));
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

    }
}