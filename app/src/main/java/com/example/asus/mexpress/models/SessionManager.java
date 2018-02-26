package com.example.asus.mexpress.models;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "Login";
    private Context _context;
    private int PRIVATE_MODE = 0;
    private String USER = "user";
    private String TYPE = "type";

    public SessionManager(Context context) {
        _context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveUser(String user) {
        editor.putString(USER, user);
        editor.commit();
    }

    public String getUser() {
        return pref.getString(USER, null);
    }

    public void saveType(String type) {
        editor.putString(TYPE, type);
        editor.commit();
    }

    public String getType() {
        return pref.getString(TYPE, null);
    }
}

