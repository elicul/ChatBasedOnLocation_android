package com.example.enzo.chatbasedonlocation;

/**
 * Created by Enzo on 28.5.2017..
 */

public class User {
    private String id;
    private String name;
    private String photoUrl;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
