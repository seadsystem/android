package com.seads.seadsv3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Home on 4/30/18.
 */

public class SeadsDevice implements Parcelable {
    private ArrayList<SeadsRoom> rooms;
    private String _raw_data;
    private String id;

    public SeadsDevice(DataSnapshot dataSnapshot){
        this.id = dataSnapshot.getKey();
        this._raw_data = dataSnapshot.toString();
        this.rooms = new ArrayList<>();
        for(DataSnapshot roomData : dataSnapshot.child("rooms").getChildren()){
            rooms.add(new SeadsRoom(roomData, this.id));
        }
    }

    public SeadsDevice(){
        this.id = "0";
    }



    public ArrayList<SeadsRoom> getRooms(){
        return this.rooms;
    }

    @Nullable
    public SeadsAppliance getDevice(String deviceName){
        for(SeadsRoom room : this.getRooms()){
            for(SeadsAppliance app : room.getApps())
                if(app.getTag().equalsIgnoreCase(deviceName)){
                    return app;
                }
        }
        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.rooms);
        dest.writeString(this._raw_data);
        dest.writeString(this.id);
    }

    protected SeadsDevice(Parcel in) {
        this.rooms = in.createTypedArrayList(SeadsRoom.CREATOR);
        this._raw_data = in.readString();
        this.id = in.readString();
    }

    public static final Creator<SeadsDevice> CREATOR = new Creator<SeadsDevice>() {
        @Override
        public SeadsDevice createFromParcel(Parcel source) {
            return new SeadsDevice(source);
        }

        @Override
        public SeadsDevice[] newArray(int size) {
            return new SeadsDevice[size];
        }
    };
}
