package com.today.gamesdk.shabdamsdk;

import android.content.Context;
import android.text.TextUtils;

import com.today.gamesdk.shabdamsdk.pref.CommonPreference;

public class Utils {

    public static void saveUserData(Context context, String name, String uname, String email, String profilePic){
        if (!TextUtils.isEmpty(name)) {
            CommonPreference.getInstance(context.getApplicationContext()).put(CommonPreference.Key.NAME, name);
        }
        if (!TextUtils.isEmpty(name)) {
            CommonPreference.getInstance(context.getApplicationContext()).put(CommonPreference.Key.UNAME, name);
        }
        if (!TextUtils.isEmpty(email)) {
            CommonPreference.getInstance(context.getApplicationContext()).put(CommonPreference.Key.EMAIL, email);
        }
        if (!TextUtils.isEmpty(profilePic)) {
            CommonPreference.getInstance(context.getApplicationContext()).put(CommonPreference.Key.PROFILE_IMAGE, profilePic);
        }
    }
}
