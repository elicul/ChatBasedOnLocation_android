package com.example.enzo.chatbasedonlocation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.enzo.chatbasedonlocation.ui.activities.SplashActivity;
import com.example.enzo.chatbasedonlocation.ui.activities.UserListingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserInfo extends AppCompatActivity {

    private static final String TAG = "AddToDatabase";

    private Button btnSubmit;
    private EditText mRange;
    private String userID;
    private Spinner spinner1;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;


    RadioGroup radioGender;
    String gender=null;
    String item;

    /////////


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserInfo.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, UserInfo.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }
    ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);


        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        mRange = (EditText) findViewById(R.id.etRange);

        ///////////

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Android");
        list.add("Java");
        list.add("Spinner Data");
        list.add("Spinner Adapter");
        list.add("Spinner Example");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter);

        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();

        // Button click Listener
        addListenerOnButton();


/////////////

        radioGender=(RadioGroup)findViewById(R.id.radioGender);
        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                int childCount = group.getChildCount();

                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);


                    if(btn.getId()== R.id.radioMale){
                        btn.setText("M");
                    }else{
                        btn.setText("F");
                    }
                    if (btn.getId() == checkedId) {

                        gender=btn.getText().toString();// here gender will contain M or F.

                    }

                }

                Log.e("Gender",gender);
            }
        });
        ////// spinner


        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                   // toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "onDataChange: Added information to database: \n" +
                        dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Submit pressed.");


                String range = mRange.getText().toString();
                String interes = addListenerOnButton();

                Log.d(TAG, "onClick: Attempting to submit to database: \n" +
                        "Range: " + range + "\n" +
                        "Gender: " + gender + "\n"

                );

                //handle the exception if the EditText fields are null
                if(!range.equals("") && !gender.equals("") ){
                  //  UserInformation userInformation = new UserInformation(range,gender, interes);
                   // myRef.child("users").child(userID).setValue(userInformation);
                    myRef.child("users").child(userID).child("range").setValue(range);
                    myRef.child("users").child(userID).child("gender").setValue(gender);
                    myRef.child("users").child(userID).child("interes").setValue(interes);

                    toastMessage("New Information has been saved.");

                  //  startActivity(new Intent(UserInfo.this, MainActivity.class));
                    UserListingActivity.startActivity(UserInfo.this);

                }else{
                    toastMessage("Fill out all the fields");

                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    /////////
// Add spinner data

    public void addListenerOnSpinnerItemSelection(){

        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    //get the selected dropdown list value

    public String addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        item = String.valueOf(spinner1.getSelectedItem());

        return item;


    }
    ///////



}
