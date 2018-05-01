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
        for(DataSnapshot item : dataSnapshot.getChildren()){
            this.key = item.child("id").getKey();
            this.value = item.child("id").toString();
        }
    }

    public String getTag(){
        return this.value;
    }
}
