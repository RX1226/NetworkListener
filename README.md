<h1 align="center">NetworkListener</h1>

<p align="center">
  <a target="_blank" href="https://www.paypal.me/RX1226" title="Donate using PayPal"><img src="https://img.shields.io/badge/paypal-donate-yellow.svg" /></a>
</p>


A network change Listener for Android.

*Inspired by [mtbfm](https://github.com/mtbfm)/**[NetworkListener](https://github.com/mtbfm/NetworkListener)**

## How to use

1. Add the JitPack repository to your build file:
```
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
2. Add the dependency:
```
    dependencies {
        implementation 'com.github.RX1226:NetworkListener:1.0.4'
    }
```

3. Add permission in AndroidManifest.xml
```
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
```
## Usage
Process flow
**note**
a. instance object and init, add listener

```
public class MainActivity extends AppCompatActivity {
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
    }
}
```
b. register observer and unregister observer
```
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
```
c. also can use kit to check network status
```
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
        Log.d(TAG, "Country Iso = " + networkKit.getNetworkCountryIso());
        Log.d(TAG, "Ip = " + networkKit.getIp());
        Log.d(TAG, "Mac address = " + networkKit.getMacAddress());
```
d. speed listener

```
    NetworkSpeedListener networkSpeedListener = new NetworkSpeedListener();
    networkSpeedListener.start((tx, rx) -> {
        Log.d(TAG, "Speed Tx = " + tx);
        Log.d(TAG, "Speed Rx = " + rx);
    });
    networkSpeedListener.stop();
```

## License

	Copyright 2020 RX1226
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
