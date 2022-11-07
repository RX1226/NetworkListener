package com.github.rx1226.network.listener.speed

import android.net.TrafficStats
import java.util.*

class NetworkSpeedListener {
    private var timer: Timer? = null

    fun start(listener: OnSpeedListener){
        val trigger = 1000
        var nowTotalTxBytes: Long
        var nowTotalRxBytes: Long
        var lastTotalTxBytes = 0L
        var lastTotalRxBytes = 0L

        timer?.cancel()
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                nowTotalTxBytes = TrafficStats.getTotalTxBytes()
                nowTotalRxBytes = TrafficStats.getTotalRxBytes()
                try {
                    listener.onChange(
                        nowTotalTxBytes - lastTotalTxBytes,
                        nowTotalRxBytes - lastTotalRxBytes
                    )
                    lastTotalTxBytes = nowTotalTxBytes
                    lastTotalRxBytes = nowTotalRxBytes
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, trigger.toLong(), trigger.toLong())
    }

    fun stop(){
        timer?. cancel()
    }
}