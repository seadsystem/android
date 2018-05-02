package com.seads.seadsv3;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Home on 4/30/18.
 */

public class SeadsRoom implements Parcelable {
    private ArrayList<SeadsAppliance> seadsApplianceArrayList;
    private String roomName;

    public SeadsRoom(DataSnapshot dataSnapshot, String seadsId){
        this.roomName = dataSnapshot.getKey();
        this.seadsApplianceArrayList = new ArrayList<>();
        for(DataSnapshot data : dataSnapshot.child("appliances").getChildren()){
            if(!data.toString().toLowerCase().contains("icon")){
                seadsApplianceArrayList.add(new SeadsAppliance(data, seadsId));
            }
        }
    }
    public String getRoomName(){
        return this.roomName;
    }

    public ArrayList<SeadsAppliance> getApps(){
        return this.seadsApplianceArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.seadsApplianceArrayList);
        dest.writeString(this.roomName);
    }

    protected SeadsRoom(Parcel in) {
        this.seadsApplianceArrayList = in.createTypedArrayList(SeadsAppliance.CREATOR);
        this.roomName = in.readString();
    }

    public static final Creator<SeadsRoom> CREATOR = new Creator<SeadsRoom>() {
        @Override
        public SeadsRoom createFromParcel(Parcel source) {
            return new SeadsRoom(source);
        }

        @Override
        public SeadsRoom[] newArray(int size) {
            return new SeadsRoom[size];
        }
    };
}
