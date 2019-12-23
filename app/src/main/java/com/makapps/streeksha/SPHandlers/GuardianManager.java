package com.makapps.streeksha.SPHandlers;

import android.content.Context;
import android.content.SharedPreferences;

import com.makapps.streeksha.Models.Guardian;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class GuardianManager {
    private static final int GUARDIAN_LIMIT = 5;
    private SharedPreferences sharedPreferences;
    public GuardianManager(Context c){
        sharedPreferences = c.getSharedPreferences("GUARDIANS",Context.MODE_PRIVATE);
    }

    private void addGuardian(Guardian g){
        if (sharedPreferences.getAll().size()<GUARDIAN_LIMIT) {
            sharedPreferences.edit().putString(g.getId(), g.toString()).apply();
        }
    }

    public boolean updateGuardian(Guardian g){
        if (sharedPreferences.contains(g.getId())){
            sharedPreferences.edit().putString(g.getId(),g.toString()).apply();
            return true;
        } else {
            return false;
        }
    }

    public int guardianRecSize(){
        return sharedPreferences.getAll().size();
    }

    public void removeGuardian(Guardian g){
        sharedPreferences.edit().remove(g.getId()).apply();
    }

    public ArrayList<Guardian> getAllGuardianList(){
        Map<String, ?> maplist = sharedPreferences.getAll();
        ArrayList<Guardian> guardians = new ArrayList<>();
        for (String key:
             maplist.keySet()) {
            try {
                JSONObject object = new JSONObject((String) maplist.get(key));
                Guardian g = new Guardian(
                        object.getString("id"),
                        object.getString("name"),
                        object.getString("email"),
                        object.getString("phone"),
                        object.getString("relationship"));
                guardians.add(g);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return guardians;
    }

    public void updateGuardianList(ArrayList<Guardian> guardians) {
        sharedPreferences.edit().clear().apply();
        for (Guardian g:
             guardians) {
            addGuardian(g);
        }
    }
}
