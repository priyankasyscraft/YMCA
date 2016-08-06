package com.ymca.AppManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Soni on 26-Jul-16.
 */
public class SharedPreference {

    public static final String PREFERENCE_NAME = "YMCA";

    public static String getSharedPrefData(Context ctx, String key) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        String value = null;
        if (prefs != null && prefs.contains(key)) {
            value = prefs.getString(key, "null");
        }
        return value;
    }

    public static void setDataInSharedPreference(Context ctx, String key,String value) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void deletePreferenceData(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
