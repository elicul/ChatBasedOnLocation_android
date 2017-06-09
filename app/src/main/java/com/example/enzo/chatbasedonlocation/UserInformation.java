package com.example.enzo.chatbasedonlocation;

/**
 * Created by User on 2/8/2017.
 */

public class UserInformation  {



    private String email;
    private String range;
    private String gender;
    private String interes;
    private Float lon;
    private Float lat;
    private String user1;
    private String user2;
    private Integer visibility;


    public UserInformation(){

    }

    public UserInformation(String email, String range, String gender, String interes) {
        this.email = email;
        this.gender = gender;
        this.range = range;
        this.interes = interes;
    }

    public UserInformation(String user1, String user2, Integer visibility) {
        this.user1 = user1;
        this.user2 = user2;
        this.visibility = visibility;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {this.email = email;}


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {this.gender = gender;}


    public String getRange() { return range; }

    public void setRange(String range) {
        this.range = range;
    }


    public String getInteres() {
        return interes;
    }

    public void setInteres (String interes) {
        this.interes = interes;
    }



    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {this.lon = lon;}

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {this.lat = lat;}

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }


}
