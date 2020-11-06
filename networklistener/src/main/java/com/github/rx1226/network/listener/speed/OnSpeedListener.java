package com.github.rx1226.network.listener.speed;

public interface OnSpeedListener {
    void onChange(long totalTxBytes, long totalRxBytes);
}
