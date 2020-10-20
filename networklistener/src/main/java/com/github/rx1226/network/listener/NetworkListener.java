package com.github.rx1226.network.listener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NetworkListener {
    private ConnectivityManager connectivityManager;
    private NetworkCallback networkCallback;

    private static NetworkListener instance;
    private NetworkListener(){
        networkCallback = new NetworkCallback();
    }

    public static NetworkListener getInstance(){
        if(instance == null){
            instance = new NetworkListener();
        }
        return instance;
    }

    public void init(Context context){
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void setOnChangeListener(OnChangeListener listener){
        networkCallback.setNetworkListener(listener);
    }

    public void onRegisterObserver(){
        if(connectivityManager != null) connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    public void unRegisterObserver() {
        if(connectivityManager != null) connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}
