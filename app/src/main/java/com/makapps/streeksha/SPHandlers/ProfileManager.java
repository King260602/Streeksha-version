package com.makapps.streeksha.SPHandlers;

import android.content.Context;
import android.content.SharedPreferences;

import com.makapps.streeksha.Models.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileManager {
    private SharedPreferences sharedPreferences;
    public ProfileManager(Context c){
        sharedPreferences = c.getSharedPreferences("USER",0);
    }

    public void updateUser(UserProfile u){
        sharedPreferences.edit().putString("data",u.toString()).apply();
    }

    public UserProfile getUserData(){
        String raw = sharedPreferences.getString("data",null);
        if (raw!=null){
            try {
                JSONObject object = new JSONObject(raw);
                return new UserProfile(
                        object.getString("id"),
                        object.getString("name"),
                        object.getString("email"),
                        object.getString("phone")
                );
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }
        return null;
    }
    public void deleteUserData(){
        sharedPreferences.edit().clear().apply();
    }
}
