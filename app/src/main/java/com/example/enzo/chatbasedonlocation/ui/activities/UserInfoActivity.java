package com.example.enzo.chatbasedonlocation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enzo.chatbasedonlocation.CustomOnItemSelectedListener;
import com.example.enzo.chatbasedonlocation.R;
import com.example.enzo.chatbasedonlocation.models.User;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "AddToDatabase";

    private Button btnSubmit, btnBack;
    private TextView mSeekBarRangeTxt;
    private String userID;
    private Spinner spinner1;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String interes;
    private Integer Km;
    String item;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("users");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        final RangeSeekBar<Integer> rangeSeekBar = (RangeSeekBar<Integer>) findViewById(R.id.rangeSeekBar);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnBack = (Button) findViewById(R.id.btnBack);
        mSeekBarRangeTxt = (TextView) findViewById(R.id.seekBarRangeTxt);
        spinner1 = (Spinner) findViewById(R.id.spinner1);

        List<String> list = new ArrayList<String>();
        list.add("Select a category");
        list.add("Education");
        list.add("Music");
        list.add("Games");
        list.add("Sports & Hobbies");
        list.add("Other");
        list.add("Business and Finance");
        list.add("Computers and Technology");
        list.add("Health");
        list.add("Family and Community");
        list.add("Religion & Spirituality");
        list.add("Radio & TV");
        list.add("Friends");
        list.add("Love & Romance");


        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();
        // Button click Listener
        addListenerOnButton();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
            }
        };
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Added information to database: \n" +
                        dataSnapshot.getValue());
                try {
                    Km = dataSnapshot.child(userID).getValue(User.class).getRange();
                    rangeSeekBar.setSelectedMaxValue(Km);
                    mSeekBarRangeTxt.setText(Km.toString() + " Km");
                } catch (Exception e){
                    Km = 10;
                    rangeSeekBar.setSelectedMaxValue(Km);
                    mSeekBarRangeTxt.setText(Km.toString() + " Km");
                }
                try {
                    interes = dataSnapshot.child(userID).getValue(User.class).getInteres();
                    int spinnerPosition = dataAdapter.getPosition(interes);
                    spinner1.setSelection(spinnerPosition);
                } catch (Exception e){
                    spinner1.setSelection(0);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        rangeSeekBar.setRangeValues(1, 200);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>(){
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                Km = maxValue;
                mSeekBarRangeTxt.setText(maxValue.toString() + " Km");
                Toast.makeText(getApplicationContext(), "Search distance chaged to " + maxValue + " km", Toast.LENGTH_LONG).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Submit pressed.");

                Float range = Float.parseFloat(Km.toString());
                String interes = addListenerOnButton();

                myRef.child(userID).child("range").setValue(range);
                myRef.child(userID).child("interes").setValue(interes);

                toastMessage("New Information has been saved.");

                UserListingActivity.startActivity(UserInfoActivity.this);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserListingActivity.startActivity(UserInfoActivity.this);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().start(getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().end(getIndexApiAction());
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void addListenerOnSpinnerItemSelection(){

        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    //get the selected dropdown list value

    public String addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        item = String.valueOf(spinner1.getSelectedItem());
        return item;

    }

    public Action getIndexApiAction() {
        return Actions.newView("UserInfo", "http://[ENTER-YOUR-URL-HERE]");
    }
}
