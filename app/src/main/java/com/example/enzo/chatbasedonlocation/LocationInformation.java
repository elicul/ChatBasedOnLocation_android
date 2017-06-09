package com.example.enzo.chatbasedonlocation;

/**
 * Created by User on 2/8/2017.
 */

public class LocationInformation {



    private Integer range;
    private Integer udaljenost;
    private String user1;
    private String user2;
    private Integer visibility;


    public LocationInformation(){

    }

    public LocationInformation(String user1, String user2, Integer visibility) {
        this.user1 = user1;
        this.user2 = user2;
        this.visibility = visibility;
    }


    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

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


    public Integer getUdaljenost() {
        return udaljenost;
    }

    public void setUdaljenost(Integer udaljenost) {
        this.udaljenost = udaljenost;
    }


    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }




}
