package com.seads.seadsv3;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Home on 4/30/18.
 */

public class SeadsRoom {
    private ArrayList<SeadsAppliance> seadsApplianceArrayList;
    private String room;

    public SeadsRoom(DataSnapshot dataSnapshot){
        this.room = dataSnapshot.getKey();
        this.seadsApplianceArrayList = new ArrayList<>();
        for(DataSnapshot data : dataSnapshot.child("appliances").getChildren()){
            if(!data.toString().toLowerCase().contains("icon")){
                seadsApplianceArrayList.add(new SeadsAppliance(data));
            }
        }
    }

    public ArrayList<SeadsAppliance> getApps(){
        return this.seadsApplianceArrayList;
    }
}
