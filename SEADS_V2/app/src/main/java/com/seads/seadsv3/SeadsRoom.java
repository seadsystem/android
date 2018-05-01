package com.seads.seadsv3;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Home on 4/30/18.
 */

public class SeadsRoom {
    private ArrayList<SeadsAppliance> seadsApplianceArrayList;

    public SeadsRoom(DataSnapshot dataSnapshot){
        seadsApplianceArrayList = new ArrayList<>();
        for(DataSnapshot data : dataSnapshot.getChildren()){
            seadsApplianceArrayList.add(new SeadsAppliance(data));
        }
    }

    public ArrayList<SeadsAppliance> getApps(){
        return this.seadsApplianceArrayList;
    }
}
