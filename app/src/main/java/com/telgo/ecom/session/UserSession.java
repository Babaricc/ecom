package com.telgo.ecom.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.telgo.ecom.User;

import java.util.Map;

public class UserSession {

    static SharedPreferences sharedPreferences;

    public UserSession(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    Uri imageUri;

    public UserSession() {
    }

    public boolean setUserPreferences(User user, Uri imageUri) {
        sharedPreferences.edit().putString("userId", user.getContact()).apply();
        sharedPreferences.edit().putString("userName", user.getName()).apply();
        sharedPreferences.edit().putString("userEmail", user.getEmail()).apply();
        sharedPreferences.edit().putString("userPassword", user.getPassword()).apply();
        sharedPreferences.edit().putString("token", user.getContact()).apply();
        sharedPreferences.edit().putString("contact", user.getContact()).apply();

        if (imageUri != null)
            sharedPreferences.edit().putString("imageUri", imageUri.toString()).apply();

        return true;
    }

    public Uri getImageUri() {
            return Uri.parse(sharedPreferences.getString("imageUri", ""));
    }

    public boolean clearSession() {
        sharedPreferences.edit().clear().apply();
        return true;
    }

    public String getContact() {
        return sharedPreferences.getString("contact", "");
    }

    public String getEmail() {
        return sharedPreferences.getString("userEmail", "");
    }
    public String getName() {
        return sharedPreferences.getString("userName", "");
    }


    public boolean getUserStatus() {
        if (sharedPreferences != null && sharedPreferences.getAll().size() != 0) {
            Map<String, ?> keys = sharedPreferences.getAll();
            Log.d("session!!!: ", "" + keys);

            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.d("session!!!: ", "" + entry.getKey() + ": " + entry.getValue());
                if (entry.getValue() == null || entry.getValue().toString().isEmpty()) return false;
            }


            return true;

        } else return false;
    }

}
