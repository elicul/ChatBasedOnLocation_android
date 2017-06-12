package com.example.enzo.chatbasedonlocation.core.users.location;

import android.location.Location;

import com.example.enzo.chatbasedonlocation.core.users.getall.GetUsersContract;
import com.example.enzo.chatbasedonlocation.models.User;
import com.example.enzo.chatbasedonlocation.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class LocationCalculation{
    private GetUsersContract.OnGetAllUsersListener mOnGetAllUsersListener;
    private User currentUser, dbUser;
    private Float distance;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;
    Integer visibility;

    public LocationCalculation(){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        currentUser = new User(firebaseUser.getUid(), firebaseUser.getEmail());
    }

    private float getDistancBetweenTwoPoints(double lat1,double lon1,double lat2,double lon2) {

        float[] distance = new float[2];
        Location.distanceBetween( lat1, lon1, lat2, lon2, distance);

        return distance[0]/1000;
    }

    public void calculateVisibility(final Double lat, final Double lon) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();

                currentUser.setLat(lat);
                currentUser.setLon(lon);
                currentUser.setRange(dataSnapshot.child(currentUser.uid).getValue(User.class).getRange());

                myRef = FirebaseDatabase.getInstance().getReference();

                myRef.child("users").child(currentUser.uid).child("lat").setValue(currentUser.getLat());
                myRef.child("users").child(currentUser.uid).child("lon").setValue(currentUser.getLon());

                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();

                    dbUser = dataSnapshotChild.getValue(User.class);

                    if(dbUser.uid.equals(currentUser.uid)) {
                        continue;
                    }

                    distance = getDistancBetweenTwoPoints(currentUser.getLat(), currentUser.getLon(),
                            dbUser.getLat(), dbUser.getLon());

                    if((float)currentUser.getRange() >= distance && (float)dbUser.getRange() >= distance ){
                        visibility = 1;
                    }else{
                        visibility = 0;
                    }

                    myRef.child("locations").child(currentUser.uid + "_" + dbUser.uid).child("user_1").setValue(currentUser.getEmail());
                    myRef.child("locations").child(currentUser.uid + "_" + dbUser.uid).child("user_2").setValue(dbUser.getEmail());
                    myRef.child("locations").child(currentUser.uid + "_" + dbUser.uid).child("visibility").setValue(visibility);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetAllUsersListener.onGetAllUsersFailure(databaseError.getMessage());
            }
        });
    }
}
