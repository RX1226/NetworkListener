package com.github.rx1226.network.listener.speed

interface OnSpeedListener {
    fun onChange(totalTxBytes: Long, totalRxBytes:Long)
}