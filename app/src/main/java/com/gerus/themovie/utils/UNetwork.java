package com.gerus.themovie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by gerus-mac on 14/03/17.
 */

public class UNetwork {

    public static boolean isOnline(Context poContext){
        ConnectivityManager voConnMan = (ConnectivityManager) poContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo voNetworkInfo = voConnMan.getActiveNetworkInfo();
        return voNetworkInfo != null && voNetworkInfo.isConnectedOrConnecting();
    }

}
