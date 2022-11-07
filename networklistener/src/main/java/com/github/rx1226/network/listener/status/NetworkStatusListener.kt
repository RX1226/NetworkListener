package com.github.rx1226.network.listener.status

import android.content.Context
import android.net.ConnectivityManager

class NetworkStatusListener(context: Context) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    private val networkCallback = NetworkCallback()
    private var isRegistered = false

    fun setOnChangeListener(listener: OnChangeListener) {
        networkCallback.setOnChangeListener(listener)
    }

    fun registerObserver() {
        if (!isRegistered) {
            connectivityManager?.registerDefaultNetworkCallback(networkCallback)
            isRegistered = true
        }
    }

    fun unRegisterObserver() {
        try {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        isRegistered = false
    }
}