package com.example.enzo.chatbasedonlocation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Enzo on 14.5.2017..
 */

public class User {
    public String name;
    public String email;
    public String password;
    public String token;

    public JSONObject toJson() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("email", email);
        jo.put("password", password);
        jo.put("token", token);
        return jo;
    }
}
