package com.example.enzo.chatbasedonlocation.models;

/**
 * Created by delaroy on 4/13/17.
 */
public class User {
    public String uid;
    public String name;
    public String email;
    public String firebaseToken;

    public User(){

    }

    public User(String uid, String email, String firebaseToken){
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }
}
