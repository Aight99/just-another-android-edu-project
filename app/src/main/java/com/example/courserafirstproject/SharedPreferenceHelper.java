package com.example.courserafirstproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SharedPreferenceHelper {
    public static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";
    public static final String USERS_KEY = "USERS_KEY";

    // Honestly, I really don't know what the hell it is
    public static final Type USERS_TYPE = new TypeToken<List<User>>() {
    }.getType();

    private SharedPreferences mSharedPreferences;
    private Gson mGson = new Gson();

    public SharedPreferenceHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public List<User> getUsers() {
        // Exception solved by rebuilding project
        String debugStr = (mSharedPreferences.getString(USERS_KEY, USERS_KEY));
        try {
            List<User> users = mGson.fromJson(debugStr, USERS_TYPE);
            return users != null ? users : new ArrayList<User>();
        } catch (Exception e) {
            Log.d("PleaseHelpMe", e.getMessage());
        }
        return new ArrayList<User>();
    }

    public boolean addNewUser(User user) {
        List<User> users = getUsers();
        for (User u : users) {
            if (user.getUsername().equalsIgnoreCase(u.getUsername())) {
                return false;
            }
        }
        users.add(user);
        mSharedPreferences.edit().putString(USERS_KEY, mGson.toJson(users, USERS_TYPE)).apply();
        return true;
    }
}
