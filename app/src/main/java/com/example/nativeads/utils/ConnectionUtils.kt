package com.example.nativeads.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionUtils(var context: Context) {

    fun isConnected(): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiInfo: NetworkInfo? =
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileInfo: NetworkInfo? =
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        return wifiInfo != null && wifiInfo.isConnected || mobileInfo != null && mobileInfo.isConnected
    }

}