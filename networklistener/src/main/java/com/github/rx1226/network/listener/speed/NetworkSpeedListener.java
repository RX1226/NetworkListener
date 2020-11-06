package com.github.rx1226.network.listener.speed;

import android.net.TrafficStats;
import android.os.Build;
import android.os.CountDownTimer;

import androidx.annotation.RequiresApi;

import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NetworkSpeedListener {
    private Timer timer;
    private long nowTotalTxBytes;
    private long nowTotalRxBytes;
    private long lastTotalTxBytes;
    private long lastTotalRxBytes;

    public void start(OnSpeedListener listener){
        int trigger = 1000;
        lastTotalTxBytes = TrafficStats.getTotalTxBytes();
        lastTotalRxBytes = TrafficStats.getTotalRxBytes();
        new CountDownTimer(trigger, trigger) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        nowTotalTxBytes = TrafficStats.getTotalTxBytes();
                        nowTotalRxBytes = TrafficStats.getTotalRxBytes();
                        try {
                            listener.onChange(
                                    (nowTotalTxBytes - lastTotalTxBytes),
                                    (nowTotalRxBytes - lastTotalRxBytes)
                            );
                            lastTotalTxBytes = nowTotalTxBytes;
                            lastTotalRxBytes = nowTotalRxBytes;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, trigger, trigger);
            }
        }.start();
    }

    public void stop() {
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
}
