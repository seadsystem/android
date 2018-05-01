package com.seads.seadsv3;

import com.google.firebase.database.DataSnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

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
        this.rooms = new ArrayList<>();
        for(DataSnapshot roomData : dataSnapshot.child("rooms").getChildren()){
            rooms.add(new SeadsRoom(roomData));
        }
    }

    public ArrayList<SeadsRoom> getRooms(){
        return this.rooms;
    }

}
