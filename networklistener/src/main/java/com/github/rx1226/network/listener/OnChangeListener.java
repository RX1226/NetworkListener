package com.github.rx1226.network.listener;

import android.net.Network;

public interface OnChangeListener {
    void onChange(String state, Network network);
}
