package com.github.rx1226.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkKit {
    //檢查是否有網路
    public static boolean isNetworkConnect(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null) return networkInfo.isConnected();
        }
        return false;
    }
    //檢查是否有Wifi
    public static boolean isWifiConnected(Context context) {
        return isConnected(context, ConnectivityManager.TYPE_WIFI);
    }
    //檢查是否有手機網路
    public static boolean isMobileConnected(Context context) {
        return isConnected(context, ConnectivityManager.TYPE_MOBILE);
    }
    private static boolean isConnected(Context context, int type){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(type);
            if(networkInfo != null) return networkInfo.isConnected();
        }
        return false;
    }

    private static float valueOfMB(float bytes){
        return bytes / 1048576; // MB = Bytes * 1024 * 1024
    }

}
