package com.today.gamesdk.shabdamsdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.clevertap.android.sdk.CleverTapAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.today.gamesdk.shabdamsdk.pref.CommonPreference;
import com.today.gamesdk.shabdamsdk.ui.activity.ShabdamBaseActivity;
import com.today.gamesdk.shabdamsdk.ui.activity.ShabdamPaheliActivity;
import com.today.gamesdk.shabdamsdk.ui.activity.TutorialActivityShabdam;
import com.today.gamesdk.shabdamsdk.ui.englishShabdam.englishGameActivity.EnglishGameActivity;

import org.jetbrains.annotations.NotNull;

public  class ShabdamSplashActivity extends ShabdamBaseActivity implements GameView {
    private GamePresenter gamePresenter;
    private String userId, name, u_name, email, profile_image;
    private String token;
    private String language;

   /* public static void startShabdam(@NonNull Context context, @NonNull String userId, @NonNull String name, String uName, String email, String profile_image, String rewardAdId, String interstitialsAdId, String bannerAdId){
        if(context == null){
            Log.e(Constants.SHABDAM_TAG, "context cannot be null");
            return;
        }

        if(!TextUtils.isEmpty(userId)){
            Log.e(Constants.SHABDAM_TAG, "User Id cannot be null");
            return;
        }

        if(!TextUtils.isEmpty(name)){
            Log.e(Constants.SHABDAM_TAG, "Name cannot be null");
            return;
        }

        Intent intent = new Intent(context, ShabdamSplashActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("name", name);
        intent.putExtra("uname", uName);
        intent.putExtra("email", email);
        intent.putExtra("profile_image", profile_image);
        context.startActivity(intent);
    }*/

    public static void startShabdam(@NotNull Context context, @NotNull String appPackageName,@NotNull String appUniqueId){
        Intent intent = new Intent(context, ShabdamSplashActivity.class);
        intent.putExtra("applicationId", appPackageName);
        intent.putExtra("appUniqueId", appUniqueId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_shabdam_main);

        Intent intent = getIntent();
       /* language = intent.getStringExtra("Language");
        CommonPreference.getInstance(this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,language);*/

        if(getIntent() != null && getIntent().hasExtra("applicationId")){
            CommonPreference.getInstance(this).put("applicationId", getIntent().getStringExtra("applicationId"));
        }

        try {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    try {
                        token = task.getResult();

                        CommonPreference.getInstance(ShabdamSplashActivity.this).put(CommonPreference.Key.DEVICE_TOKEN,token);
                        Log.d("beemen", token);
                        if (token != null) {

                            CleverTapAPI.getDefaultInstance(ShabdamSplashActivity.this).pushFcmRegistrationId(token, true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("deviceId", token);
        CleverTapAPI cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        cleverTapAPI.pushProfile(profileUpdate);*/


        /*if (!TextUtils.isEmpty(userId)) {
            AddUserRequest request = new AddUserRequest();
            request.setUserId(userId);
            request.setName(name);
            request.setUname(u_name);
            request.setEmail(email);
            request.setProfileimage(profile_image);
            gamePresenter.addUser(request);
        }else if(email != null){
            SignupRequest signupRequest = new SignupRequest();
            signupRequest.setEmail(email);
            signupRequest.setName(name);
            signupRequest.setUname(name);
            signupRequest.setProfileimage(profile_image);
            gamePresenter.signUpUser(signupRequest);
        }*/
        startFlow();
    }

    private void startFlow() {
        if (!CommonPreference.getInstance(ShabdamSplashActivity.this.getApplicationContext()).getBoolean(CommonPreference.Key.IS_TUTORIAL_SHOWN, false)) {
            startActivity(new Intent(ShabdamSplashActivity.this, TutorialActivityShabdam.class));
            finish();
        } else if (!CommonPreference.getInstance(ShabdamSplashActivity.this.getApplicationContext()).getBoolean(CommonPreference.Key.IS_RULE_SHOWN, false)) {
            startActivity(new Intent(ShabdamSplashActivity.this, ShabdamPaheliActivity.class));
            finish();
        } else {
            if(!TextUtils.isEmpty(CommonPreference.getInstance(ShabdamSplashActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(ShabdamSplashActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")){
                startActivity(new Intent(ShabdamSplashActivity.this, GameActivity.class));
                finish();
            }else{
                startActivity(new Intent(ShabdamSplashActivity.this, EnglishGameActivity.class));
                finish();
            }

        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onAddUser(com.today.gamesdk.shabdamsdk.model.adduser.Data data) {
        if (data != null) {
            if (data.getId() != "0") {
                CommonPreference.getInstance(this.getApplicationContext()).put(CommonPreference.Key.GAME_USER_ID, String.valueOf(data.getId()));
                Intent intent = new Intent(ShabdamSplashActivity.this, TutorialActivityShabdam.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(gamePresenter != null){
            gamePresenter.onDestroy();
        }
    }
}
