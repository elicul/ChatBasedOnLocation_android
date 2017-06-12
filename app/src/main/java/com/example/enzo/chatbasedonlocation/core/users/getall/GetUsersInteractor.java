package com.example.enzo.chatbasedonlocation.core.users.getall;

import android.util.Log;

import com.example.enzo.chatbasedonlocation.models.User;
import com.example.enzo.chatbasedonlocation.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class GetUsersInteractor implements GetUsersContract.Interactor {
    private static final String TAG = "GetUsersInteractor";
    private GetUsersContract.OnGetAllUsersListener mOnGetAllUsersListener;
    //add Firebase Database stuff
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private User currentUser;

    List<User> users = new ArrayList<>();

    public GetUsersInteractor(GetUsersContract.OnGetAllUsersListener onGetAllUsersListener) {
        this.mOnGetAllUsersListener = onGetAllUsersListener;
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        currentUser = new User(firebaseUser.getUid(), firebaseUser.getEmail());
    }

    public boolean userAlreadyAdded(User dbUser){
        boolean result = false;
        for (User listUser: users) {
            if(listUser.getEmail().equals(dbUser.getEmail())){
                result = true;
            }
        }
        return result;
    }

    @Override
    public void getAllUsersFromFirebase() {
        // Read from the database
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("locations").addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    final Users visibleUsers = child.getValue(Users.class);
                    if(visibleUsers.getVisibility() == 1 && currentUser.email.equals(visibleUsers.getUser_1())) {
                        myRef.child("users").addValueEventListener(new ValueEventListener()  {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                currentUser.setInteres(dataSnapshot.child(currentUser.uid).getValue(User.class).getInteres());
                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                for (DataSnapshot child : children) {
                                    User dbUser = child.getValue(User.class);
                                    if(dbUser.email.equals(visibleUsers.getUser_2())) {
                                        if(currentUser.getInteres().equals(dbUser.getInteres())){
                                            if(!userAlreadyAdded(dbUser))
                                            {
                                                users.add(dbUser);
                                            }
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.w(TAG, "Failed to read users!", error.toException());
                            }
                        });
                    }
                }
                mOnGetAllUsersListener.onGetAllUsersSuccess(users);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read users!", error.toException());
            }
        });
    }

    @Override
    public void deleteUsersList() {
        users.clear();
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