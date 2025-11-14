package com.sahaj.sahaj.database;

import android.content.Context;
import android.content.SharedPreferences;

public class DBManager {
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public DBManager(Context context) {
        preferences = context.getSharedPreferences("PREFS_PRIVATE", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setBrandIndex(int value) {
        editor.putInt("BrandIndex", value);
        editor.apply();
    }

    public int getBrandIndex() {
        return preferences.getInt("BrandIndex", 0);
    }

    public void setModelIndex(int value) {
        editor.putInt("ModelIndex", value);
        editor.apply();
    }

    public int getModelIndex() {
        return preferences.getInt("ModelIndex", 0);
    }

    public void setDPIndex(int value) {
        editor.putInt("DPIndex", value);
        editor.apply();
    }

    public int getDPIndex() {
        return preferences.getInt("DPIndex", 0);
    }

    public void setEMIIndex(int value) {
        editor.putInt("EMIIndex", value);
        editor.apply();
    }

    public int getEMIIndex() {
        return preferences.getInt("EMIIndex", 0);
    }
}
