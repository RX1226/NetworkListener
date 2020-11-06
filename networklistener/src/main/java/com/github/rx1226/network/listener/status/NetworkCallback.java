package com.github.rx1226.network.listener.status;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NetworkCallback extends ConnectivityManager.NetworkCallback{
    private OnChangeListener listener;

    public void setNetworkListener(OnChangeListener listener){
        this.listener = listener;
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        //網路已連接
        if(listener != null) listener.onChange(NetworkState.AVAILABLE, network);
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        //網路已斷開
        if(listener != null) listener.onChange(NetworkState.LOST, network);
    }

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                //網路變更為Wifi, 如果是從手機網路切到wifi網路也會觸發
                if(listener != null) listener.onChange(NetworkState.CHANGE_TO_WIFI, network);
            } else {
                //網路變更為手機網路
                if(listener != null) listener.onChange(NetworkState.CHANGE_TO_MOBILE, network);
            }
        }
    }
}
