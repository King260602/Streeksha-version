package com.makapps.streeksha.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Guardian {
    private String id,name,email,phone,relationship;

    public Guardian(String id, String name, String email, String phone, String relationship) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.relationship = relationship;
    }

    private Guardian(){

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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",this.id);
            object.put("name",this.name);
            object.put("email",this.email);
            object.put("phone",this.phone);
            object.put("relationship",this.relationship);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
