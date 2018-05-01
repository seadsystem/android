package com.seads.seadsv3;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

/**
 * Created by Home on 4/30/18.
 */

public class SeadsAppliance {
    private String key;
    private String value;
    public SeadsAppliance(DataSnapshot dataSnapshot){
        this.key = dataSnapshot.getKey();
        for(DataSnapshot item : dataSnapshot.getChildren()){
            this.value = item.getValue().toString();
        }
    }

    public String getTag(){
        return this.value;
    }
}
