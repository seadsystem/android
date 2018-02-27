package android.sead_systems.seads.graph;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Home on 2/22/18.
 */

public class SpinnerHandler implements AdapterView.OnItemSelectedListener {
    private final String[] selections = {};


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Log.d("SPINNER: ", "Selected position" + pos+":"+parent.getItemAtPosition(pos));
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

    }
}
