package com.github.rx1226.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
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

    public static int getTxKbps(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if(networkCapabilities != null) return networkCapabilities.getLinkUpstreamBandwidthKbps();
        }
        return 0;
    }

    public static int getRxKbps(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if(networkCapabilities != null) return networkCapabilities.getLinkDownstreamBandwidthKbps();
        }
        return 0;
    }

    public static float getTxMB(Context context) {
        return getTxKbps(context) / 1024f;
    }

    public static float getRxMB(Context context) {
        return getRxKbps(context) / 1024f;
    }
}
