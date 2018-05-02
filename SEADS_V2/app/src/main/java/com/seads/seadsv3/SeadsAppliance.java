package com.seads.seadsv3;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

/**
 * Created by Home on 4/30/18.
 */

public class SeadsAppliance implements Parcelable {
    private String key;
    private String value;
    private String devId;
    public SeadsAppliance(DataSnapshot dataSnapshot, String seadsId){
        this.key = dataSnapshot.getKey();
        for(DataSnapshot item : dataSnapshot.getChildren()){
            this.value = item.getValue().toString();
        }
        this.devId = seadsId.replaceAll("\"", "");
    }

    public String getSeadsId(){return this.devId;}

    public String getTag(){
        return this.key;
    }

    public String getQueryId(){
        return this.value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.value);
        dest.writeString(this.devId);
    }

    protected SeadsAppliance(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
        this.devId = in.readString();
    }

    public static final Creator<SeadsAppliance> CREATOR = new Creator<SeadsAppliance>() {
        @Override
        public SeadsAppliance createFromParcel(Parcel source) {
            return new SeadsAppliance(source);
        }

        @Override
        public SeadsAppliance[] newArray(int size) {
            return new SeadsAppliance[size];
        }
    };
}
