package com.rx1226.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.rx1226.network.NetworkKit;
import com.github.rx1226.network.listener.speed.NetworkSpeedListener;
import com.github.rx1226.network.listener.status.NetworkStatusListener;
import com.github.rx1226.network.listener.status.NetworkState;

public class MainActivity extends AppCompatActivity{
    private final static String TAG = "MainActivity";
    private NetworkStatusListener networkStatusListener;
    //listener speed tx rx
    private final NetworkSpeedListener networkSpeedListener = new NetworkSpeedListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Listener to network connect statue
        networkStatusListener = new NetworkStatusListener(this);
        networkStatusListener.setOnChangeListener((state, network) -> {
            switch (state){
                case NetworkState.AVAILABLE:
                    Log.d(TAG, "Network is " + NetworkState.AVAILABLE);
                    break;
                case NetworkState.LOST:
                    Log.d(TAG, "Network is " + NetworkState.LOST);
                    break;
                case NetworkState.CHANGE_TO_MOBILE:
                    Log.d(TAG, "Network is " + NetworkState.CHANGE_TO_MOBILE);
                    break;
                case NetworkState.CHANGE_TO_WIFI:
                    Log.d(TAG, "Network is " + NetworkState.CHANGE_TO_WIFI);
                    break;
            }
        });

        NetworkKit networkKit = new NetworkKit(this);
        //kit to check connect
        if(networkKit.isNetworkConnect()){
            Log.d(TAG, "Network is connect");
        }
        if(networkKit.isMobileConnected()){
            Log.d(TAG, "Mobile is connect");
        }
        if(networkKit.isWifiConnected()){
            Log.d(TAG, "Wifi is connect");
        }
        if(networkKit.isEthernetConnected()){
            Log.d(TAG, "Ethernet is connect");
        }
        Log.d(TAG, "Country Iso = " + networkKit.getNetworkCountryIso());
        Log.d(TAG, "Ip = " + networkKit.getIp());
        Log.d(TAG, "Mac address = " + networkKit.getMacAddress());
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkStatusListener.registerObserver();
    }

    @Override
    public void onStop() {
        networkStatusListener.unRegisterObserver();
        super.onStop();
    }

    public void start(View view){
        networkSpeedListener.start((tx, rx) -> {
            Log.d(TAG, "Speed Tx = " + tx);
            Log.d(TAG, "Speed Rx = " + rx);
        });
    }
    public void stop(View view){
        networkSpeedListener.stop();
    }
}
