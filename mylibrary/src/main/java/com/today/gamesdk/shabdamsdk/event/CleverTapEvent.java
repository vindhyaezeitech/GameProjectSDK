package com.today.gamesdk.shabdamsdk.event;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.clevertap.android.sdk.CleverTapAPI;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;
import java.util.Map;

public class CleverTapEvent {
    private CleverTapAPI clevertapDefaultInstance;
    private FirebaseAnalytics firebaseAnalytics;
    private static CleverTapEvent cleverTapEvent;

    public void initializedCleverTap(Context context) {
        if (clevertapDefaultInstance == null) {
            clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(context.getApplicationContext());
        }
    }

    public void initializedFirebase(Context context) {
        if (firebaseAnalytics == null) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context.getApplicationContext());
        }
    }

    public CleverTapEvent(Context context) {
        initializedFirebase(context);
        initializedCleverTap(context);
    }

    public void createOnlyEvent(String event_name) {
        if (clevertapDefaultInstance != null){
            HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
            prodViewedAction.put(CleverTapEventConstants.DEVICE_TYPE, CleverTapEventConstants.ANDROID);
            clevertapDefaultInstance.pushEvent(event_name, prodViewedAction);
            Log.d("CLEVERTAP_EVENTS", event_name );

        }
        if(firebaseAnalytics != null){
            Bundle bundle = new Bundle();
            bundle.putString(CleverTapEventConstants.DEVICE_TYPE, CleverTapEventConstants.ANDROID);
            firebaseAnalytics.logEvent(event_name, bundle);
        }
    }


    public void iterateUsingLambda(String event_name, HashMap<String, Object> map) {
        Bundle bundle = new Bundle();
        // map.forEach((k, v) -> bundle.putString(k, (String) v));

        for (Map.Entry<String,Object> entry : map.entrySet()){
            bundle.putString(entry.getKey(), String.valueOf(entry.getValue()));
        }

        bundle.putString(CleverTapEventConstants.DEVICE_TYPE, CleverTapEventConstants.ANDROID);

        firebaseAnalytics.logEvent(event_name, bundle);
    }

    public static CleverTapEvent getCleverTapEvents(Context context) {
        if (cleverTapEvent == null) {
            return new CleverTapEvent(context);
        }
        return cleverTapEvent;
    }

    private CleverTapEvent()
    {

    }
}
