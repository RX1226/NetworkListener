package com.rx1226.sample;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.rx1226.network.NetworkKit;
import com.github.rx1226.network.listener.NetworkListener;
import com.github.rx1226.network.listener.NetworkState;

public class MainActivity extends AppCompatActivity{
    private NetworkListener networkListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Listener to network connect statue
        networkListener = new NetworkListener(this);
        networkListener.setOnChangeListener((state, network) -> {
            switch (state){
                case NetworkState.AVAILABLE:
                    Log.d("TAG", "Network is " + NetworkState.AVAILABLE);
                    break;
                case NetworkState.LOST:
                    Log.d("TAG", "Network is " + NetworkState.LOST);
                    break;
                case NetworkState.CHANGE_TO_MOBILE:
                    Log.d("TAG", "Network is " + NetworkState.CHANGE_TO_MOBILE);
                    break;
                case NetworkState.CHANGE_TO_WIFI:
                    Log.d("TAG", "Network is " + NetworkState.CHANGE_TO_WIFI);
                    break;
            }
        });

        //kit to check connect
        if(NetworkKit.isNetworkConnect(this)){
            //if network is connect
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkListener.registerObserver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        networkListener.unRegisterObserver();
    }
}
