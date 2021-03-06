package com.github.rx1226.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class NetworkKit {
    private final Context context;
    private final ConnectivityManager connectivityManager;
    public NetworkKit(Context context){
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }
    //檢查是否有網路
    public boolean isNetworkConnect(){
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null) && networkInfo.isConnected();
    }
    //檢查是否有Wifi
    public boolean isWifiConnected() {
        return isConnected(ConnectivityManager.TYPE_WIFI);
    }
    //檢查是否有手機網路
    public boolean isMobileConnected() {
        return isConnected(ConnectivityManager.TYPE_MOBILE);
    }
    //檢查是否有網路
    private boolean isConnected(int type){
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(type);
        return (networkInfo != null) && networkInfo.isConnected();
    }

    private float valueOfMB(float bytes){
        return bytes / 1048576; // MB = Bytes * 1024 * 1024
    }
    //取得國家碼
    public String getNetworkCountryIso() {
        return ((android.telephony.TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkCountryIso();
    }
    //取得ip
    public String getIp() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addresses = Collections.list(intf.getInetAddresses());
                for (InetAddress inetAddress : addresses) {
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace();}
        return "0.0.0.0";
    }
    //取得mac
    public String getMacAddress() {
        try {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                if (!networkInterface.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = networkInterface.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder result = new StringBuilder();
                for (byte b : macBytes) {
                    result.append(String.format("%02X:",b));
                }

                if (result.length() > 0) {
                    result.deleteCharAt(result.length() - 1);
                }
                return result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00"; //官方後來只能取得的固定值
    }
}