package com.github.rx1226.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.TelephonyManager
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*

class NetworkKit(context: Context) {
    private var connectivityManager :ConnectivityManager
    private var telephonyManager :TelephonyManager

    init {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    private fun getNetworkCapabilities(): NetworkCapabilities?{
        return try {
            val currentNetwork = connectivityManager.activeNetwork
            connectivityManager.getNetworkCapabilities(currentNetwork) as NetworkCapabilities
        } catch (e: Exception) {
            null
        }
    }
    //檢查是否有網路
    fun isNetworkConnect() = isWifiConnected() || isMobileConnected()
            || isEthernetConnected()

    //檢查是否有Wifi
    fun isWifiConnected(): Boolean {
        return getNetworkCapabilities()?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
    }

    //檢查是否有手機網路
    fun isMobileConnected(): Boolean {
        return getNetworkCapabilities()?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
    }

    //檢查是否實體網路
    fun isEthernetConnected(): Boolean {
        return getNetworkCapabilities()?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ?: false
    }

    private fun valueOfMB(bytes: Float): Float {
        return bytes / 1048576 // MB = Bytes * 1024 * 1024
    }

    //取得國家碼
    fun getNetworkCountryIso(): String {
        return telephonyManager.networkCountryIso
    }

    //取得ip
    fun getIp(): String? {
        try {
            val interfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (networkInterface in interfaces) {
                val addresses: List<InetAddress> = Collections.list(networkInterface.inetAddresses)
                for (inetAddress in addresses) {
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "0.0.0.0"
    }

    //取得mac
    fun getMacAddress(): String {
        try {
            val networkInterfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (networkInterface in networkInterfaces) {
                if (!networkInterface.name.equals("wlan0", ignoreCase = true)) continue
                val macBytes = networkInterface.hardwareAddress ?: return ""
                val result = StringBuilder()
                for (b in macBytes) {
                    result.append(String.format("%02X:", b))
                }
                if (result.isNotEmpty()) {
                    result.deleteCharAt(result.length - 1)
                }
                return result.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "02:00:00:00:00:00" //官方後來只能取得的固定值
    }
}