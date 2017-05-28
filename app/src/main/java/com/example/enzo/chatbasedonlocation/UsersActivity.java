package com.example.enzo.chatbasedonlocation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    public static final String USERS_CHILD = "users";
    public static final String ANONYMOUS = "anonymous";

    private String mUsername;
    private SharedPreferences mSharedPreferences;

    private RecyclerView mUserRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference mUsersReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = ANONYMOUS;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
        }

        mUserRecyclerView = (RecyclerView) findViewById(R.id.userRecyclerView);
        mLayoutManager = new GridLayoutManager(mContext,1);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mUsersReference = mFirebaseDatabaseReference.child(USERS_CHILD);
        mContext = getApplicationContext();



        mUsersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final List<String> usersList = new ArrayList<String>();
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    usersList.add(user.getName());
                    Log.e("Get Data : ", user.getName());
                }
                mAdapter = new UsersAdapter(mContext, usersList);
                mUserRecyclerView.setLayoutManager(mLayoutManager);
                mUserRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(DatabaseError dbError) {
                Log.e("The read failed: " , dbError.getMessage());
            }
        });


    }
}
