package com.today.gamesdk.shabdamsdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ToastUtils {

    public static void show(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (mgr != null) {
            netInfo = mgr.getActiveNetworkInfo();
        }

        if (netInfo != null) {
            return netInfo.isConnected();
        } else {
            return false;
        }
    }

}
