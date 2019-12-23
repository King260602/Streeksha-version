package com.makapps.streeksha.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile {
    private String id,name,email,phone,oauth;

    public UserProfile(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    private UserProfile(){
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",this.id);
            object.put("name",this.name);
            object.put("email",this.email);
            object.put("phone",this.phone);
            object.put("outh",this.oauth);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public String getOauth() {
        return oauth;
    }

    public void setOauth(String oauth) {
        this.oauth = oauth;
    }
}
