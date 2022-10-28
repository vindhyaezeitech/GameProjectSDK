package com.today.gamesdk.shabdamsdk.pref;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class CommonPreference {
    private static final String SETTINGS_NAME = "shabdam_pref";
    private static CommonPreference sSharedPrefs;
    private final SharedPreferences mPref;
    private final boolean mBulkUpdate = false;
    private SharedPreferences.Editor mEditor;

    private CommonPreference(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    public static CommonPreference getInstance(Context context) {
        if (sSharedPrefs == null) {
            synchronized (CommonPreference.class) {
                if (sSharedPrefs == null) {
                    sSharedPrefs = new CommonPreference(context.getApplicationContext());
                }
            }
        }
        return sSharedPrefs;
    }

    public void put(String key, String val) {
        doEdit();
        mEditor.putString(key, val);
        doCommit();
    }

    public void put(String key, int val) {
        doEdit();
        mEditor.putInt(key, val);
        doCommit();
    }

    public void put(String key, boolean val) {
        doEdit();
        mEditor.putBoolean(key, val);
        doCommit();
    }

    public void put(String key, float val) {
        doEdit();
        mEditor.putFloat(key, val);
        doCommit();
    }

    public void put(String key, double val) {
        doEdit();
        mEditor.putString(key, String.valueOf(val));
        doCommit();
    }

    public void put(String key, long val) {
        doEdit();
        mEditor.putLong(key, val);
        doCommit();
    }

    public String getString(String key, String defaultValue) {
        return mPref.getString(key, defaultValue);
    }


    public String getString(String key) {

        return mPref.getString(key, "");
    }

    public String getAppLanguageString(String key) {
        return !TextUtils.isEmpty(mPref.getString(Key.SHABDAM_LANGUAGE, null))? mPref.getString(Key.SHABDAM_LANGUAGE,""):mPref.getString(key, null);
    }

    public String getPackageString(String key) {
        return mPref.getString(key, "com.today.gamesdk");
    }

    public String getUniqueAppId() {
        return mPref.getString("appUniqueId", null);
    }


    public boolean getBoolean(String key, boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }

    public Long getLong(String key) {
        return mPref.getLong(key, 5);
    }

    public Long getLongDefaultZero(String key) {
        return mPref.getLong(key, 0);
    }

    public Integer getInteger(String key) {
        return mPref.getInt(key, 0);
    }

    private boolean isAddUser() {
        return TextUtils.isEmpty(getString(Key.GAME_USER_ID));
    }

    public String getAddUser() {
        return isAddUser() ? "" : getString(Key.GAME_USER_ID);
    }

    private boolean isUserid() {
        return TextUtils.isEmpty(getString(Key.USER_ID));
    }

    public String getUserid() {
        return isUserid() ? "" : getString(Key.USER_ID);
    }

    private boolean isName() {
        return TextUtils.isEmpty(getString(Key.NAME));
    }

    public String getName() {
        return isName() ? "" : getString(Key.NAME);
    }

    private boolean isUname() {
        return TextUtils.isEmpty(getString(Key.UNAME));
    }

    public String getUname() {
        return isUname() ? "" : getString(Key.UNAME);
    }

    private boolean isEmail() {
        return TextUtils.isEmpty(getString(Key.EMAIL));
    }

    public String getEmail() {
        return isEmail() ? "" : getString(Key.EMAIL);
    }

    private boolean isProfileImage() {
        return TextUtils.isEmpty(getString(Key.PROFILE_IMAGE));
    }

    public String getProfileImage() {
        return isProfileImage() ? "" : getString(Key.PROFILE_IMAGE);
    }

    private boolean isType() {
        return TextUtils.isEmpty(getString(Key.TYPE));
    }

    public String getType() {
        return isType() ? "" : getString(Key.TYPE);
    }

    public boolean getSoundState(){
        return getBoolean(Key.SOUND_STATE, true);
    }

    public void saveSoundState(boolean soundState){
        put(Key.SOUND_STATE, soundState);
    }

    public void remove(String... keys) {
        doEdit();
        for (String key : keys) {
            mEditor.remove(key).commit();
        }
        doCommit();
    }

    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    /*public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.commit();
        mEditor = null;
    }*/

    @SuppressLint("CommitPrefEdits")
    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }

    public class Key {
        public static final String USER_ID = "user_id";
        public static final String IS_FIRST_TIME = "is_first_time";
        public static final String NAME = "name";
        public static final String UNAME = "uname";
        public static final String EMAIL = "email";
        public static final String PROFILE_IMAGE = "profile_image";
        public static final String GAME_USER_ID = "game_user_id";
        public static final String TYPE = "type";
        public static final String IS_TUTORIAL_SHOWN = "is_tut_shown";
        public static final String IS_RULE_SHOWN = "is_rule_shown";

        public static final String SOUND_STATE = "sound_state";

        public static final String DEVICE_TOKEN = "device_token";
        public static final String IS_LOGOUT = "is_logout";

        public static final String SHABDAM_APP_LANGUAGE = "SHABDAM_APP_LANGUAGE";
        public static final String HINDI = "hindi";
            public static final String ENGLISH = "english";

        public static final String SHABDAM_LANGUAGE = "shabdam_language";

        public static final String DEVICE_ID = "device_id";

    }

}
