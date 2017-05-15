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

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public static User fromJson(JSONObject jsonObject) {
        User u = new User();
        // Deserialize json into object fields
        try {
            u.name = jsonObject.getString("name");
            u.email = jsonObject.getString("email");
            u.password = jsonObject.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return u;
    }
}
