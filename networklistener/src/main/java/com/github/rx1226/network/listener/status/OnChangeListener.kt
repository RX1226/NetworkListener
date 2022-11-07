package com.github.rx1226.network.listener.status

import android.net.Network

interface OnChangeListener {
    fun onChange(state: String?, network: Network?)
}