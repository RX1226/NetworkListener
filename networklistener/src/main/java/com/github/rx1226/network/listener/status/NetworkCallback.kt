package com.github.rx1226.network.listener.status

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

class NetworkCallback: ConnectivityManager.NetworkCallback() {
    private var onChangeListener: OnChangeListener? = null

    fun setOnChangeListener(onChangeListener: OnChangeListener){
        this.onChangeListener = onChangeListener
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        //網路已連接
        onChangeListener?.onChange(NetworkState.AVAILABLE, network)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        //網路已斷開
        onChangeListener?.onChange(NetworkState.LOST, network)
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                //網路變更為Wifi, 如果是從手機網路切到wifi網路也會觸發
                onChangeListener?.onChange(NetworkState.CHANGE_TO_WIFI, network)
            } else {
                //網路變更為手機網路
                onChangeListener?.onChange(NetworkState.CHANGE_TO_MOBILE, network)
            }
        }
    }
}