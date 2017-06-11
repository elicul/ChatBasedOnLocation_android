package com.example.enzo.chatbasedonlocation.core.users.getall;

import android.text.TextUtils;

import com.example.enzo.chatbasedonlocation.models.User;
import com.example.enzo.chatbasedonlocation.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import com.example.enzo.chatbasedonlocation.LocationInformation;
import com.example.enzo.chatbasedonlocation.UserInformation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class GetUsersInteractor implements GetUsersContract.Interactor {
    private static final String TAG = "GetUsersInteractor";

    private GetUsersContract.OnGetAllUsersListener mOnGetAllUsersListener;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase1, mFirebaseDatabase2,mFirebaseDatabase3,mFirebaseDatabase4;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef1, myRef2, myRef3, myRef4;
    String userID;
    String korisnik;
    Integer range, korisnik_range, distance, udaljenost2, visibility;

    List<User> users = new ArrayList<>();


    public GetUsersInteractor(GetUsersContract.OnGetAllUsersListener onGetAllUsersListener) {
        this.mOnGetAllUsersListener = onGetAllUsersListener;
    }


    @Override
    public void getAllUsersFromFirebase() {

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase1 = FirebaseDatabase.getInstance();
        mFirebaseDatabase2 = FirebaseDatabase.getInstance();
        mFirebaseDatabase3 = FirebaseDatabase.getInstance();
        mFirebaseDatabase4 = FirebaseDatabase.getInstance();

        myRef1 = mFirebaseDatabase1.getReference();
        myRef2 = mFirebaseDatabase2.getReference();
        myRef3 = mFirebaseDatabase3.getReference();


        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();


                UserInformation uInfo = new UserInformation();

                uInfo.setEmail(dataSnapshot.child(userID).getValue(UserInformation.class).getEmail());
                uInfo.setRange(dataSnapshot.child(userID).getValue(UserInformation.class).getRange());

                korisnik = uInfo.getEmail();
                //     Log.d(TAG, "korisnik tu sam " + korisnik);

                korisnik_range = Integer.parseInt(uInfo.getRange());

                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    User user = dataSnapshotChild.getValue(User.class);

                    UserInformation specimenDTO = dataSnapshotChild.getValue(UserInformation.class);

                    //uključit kad se doda lokacija
                    //  Float longitude=specimenDTO.getLon();
                    //  Float latitude=specimenDTO.getLat();

                    //range svih korinika koje izlistavamo
                    range = Integer.parseInt(specimenDTO.getRange());

                    //promjenit da izračunava distance
                    distance=5;




                    if (!TextUtils.equals(user.uid, FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        /////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        ///////////////////ovjde uzima sve korisnike, pa ih prikaže
                        users.add(user);

                        if(korisnik_range >= distance && range >= distance){
                            visibility=1;
                        }else{
                            visibility=0;
                        }

                        myRef2.child("location").child(user.uid+ "_" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child("user1").setValue(korisnik);
                        myRef2.child("location").child(user.uid+ "_" + FirebaseAuth.getInstance().getCurrentUser().getUid() ).child("user2").setValue(specimenDTO.getEmail());
                        myRef2.child("location").child(user.uid+ "_" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child("visibility").setValue(visibility);

                        myRef2.child("location").child(FirebaseAuth.getInstance().getCurrentUser().getUid()+ "_" +user.uid).child("user1").setValue(specimenDTO.getEmail());
                        myRef2.child("location").child(FirebaseAuth.getInstance().getCurrentUser().getUid()+ "_" +user.uid).child("user2").setValue(korisnik);
                        myRef2.child("location").child(FirebaseAuth.getInstance().getCurrentUser().getUid()+ "_" +user.uid).child("visibility").setValue(visibility);


                    }
                }
                mOnGetAllUsersListener.onGetAllUsersSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOnGetAllUsersListener.onGetAllUsersFailure(databaseError.getMessage());
            }
        });

        // Read from the database
        myRef3.child("location").addValueEventListener(new ValueEventListener()  {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get all of the children at this level.
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                // shake hands with each of them.'
                for (DataSnapshot child : children) {
                    //  UserInformation specimenDTO = child.getValue(UserInformation.class);

                    UserInformation info = child.getValue(UserInformation.class);

                    if(info.getVisibility()==1 && korisnik==info.getUser1()) {

                        Log.d(TAG, "vidljivost " + info.getUser2());
                        /////////////////////////////////////////ovjde treba dodat usere koje želimo prikazat na ekranu
                        //         users.add(info);

                    }
                }

                //   mOnGetAllUsersListener.onGetAllUsersSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void getChatUsersFromFirebase() {
        /*FirebaseDatabase.getInstance().getReference().child(Constants.ARG_CHAT_ROOMS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots=dataSnapshot.getChildren().iterator();
                List<User> users=new ArrayList<>();
                while (dataSnapshots.hasNext()){
                    DataSnapshot dataSnapshotChild=dataSnapshots.next();
                    dataSnapshotChild.getRef().
                    Chat chat=dataSnapshotChild.getValue(Chat.class);
                    if(chat.)4
                    if(!TextUtils.equals(user.uid,FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        users.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }


}