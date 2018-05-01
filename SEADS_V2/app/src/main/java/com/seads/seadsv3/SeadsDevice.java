package com.seads.seadsv3;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by Home on 4/30/18.
 */

public class SeadsDevice {
    private ArrayList<SeadsRoom> rooms;
    private String _raw_data;
    private String id;

    public SeadsDevice(DataSnapshot dataSnapshot){
        this.id = dataSnapshot.getKey();
        this._raw_data = dataSnapshot.toString();
        for(DataSnapshot roomData : dataSnapshot.getChildren()){
            rooms.add(new SeadsRoom(roomData));
        }
    }

    public ArrayList<SeadsRoom> getRooms(){
        return this.rooms;
    }

}
