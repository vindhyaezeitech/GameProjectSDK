package com.today.gamesdk.shabdamsdk.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class ShabdamLanguagePreference {

    private static ShabdamLanguagePreference instance;
    private static SharedPreferences pref;

    private ShabdamLanguagePreference(Context context)
    {
        if (context != null)
        {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }

    }

    public static ShabdamLanguagePreference getInstance(Context context)
    {
        if (instance == null || pref == null)
        {
            instance = new ShabdamLanguagePreference(context);
        }
        return instance;
    }


    public String getLanguage()
    {
        return pref.getString("appLanguageshabdam", "");
    }

    public void setLanguage(String b)
    {
        pref.edit().putString("appLanguageshabdam", b).apply();
    }
}

