package com.github.rx1226.network.listener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NetworkListener {
    private ConnectivityManager connectivityManager;
    private NetworkCallback networkCallback;

    public NetworkListener(Context context){
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new NetworkCallback();
    }

    public void setOnChangeListener(OnChangeListener listener){
        networkCallback.setNetworkListener(listener);
    }

    public void onRegisterObserver(){
        if(connectivityManager != null) connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    public void unRegisterObserver() {
        if(connectivityManager != null){
            try {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
