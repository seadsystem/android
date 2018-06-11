package com.seads.seadsv3;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by root on 5/31/18.
 */

public class RoomBuilder {

    public static void initSeads(FirebaseUser user, FirebaseAuth mAuth){
        Log.d("RoomBuilder", user.getUid()+"="+user.getEmail());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mSnapshot = mDatabase.child("users").child(user.getUid()).child("devices");

        DatabaseReference seads = mSnapshot.child("466419818");
        seads.child("name").setValue("Real Device");
        seads.child("rooms").child("Kitchen").child("appliances").child("Oven").child("id").setValue("Panel1");
        seads.child("rooms").child("Kitchen").child("appliances").child("Refrigerator").child("id").setValue("Panel2");
        seads.child("rooms").child("Living Room").child("appliances").child("Solar Panel").child("id").setValue("PowerS");
        seads.child("rooms").child("Living Room").child("appliances").child("Television").child("id").setValue("Panel3");

        seads = mSnapshot.child("2038532776");
        seads.child("name").setValue("testbed");
        seads.child("rooms").child("Solar").child("appliances").child("test").child("id").setValue("Solar");
        seads.child("rooms").child("Switches").child("appliances").child("test").child("id").setValue("Grid");
    }
}
