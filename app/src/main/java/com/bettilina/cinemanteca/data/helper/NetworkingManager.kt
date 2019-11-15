package com.bettilina.cinemanteca.data.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

open class NetworkingManager(private val context: Context) {

    open fun isNetworkOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager?.activeNetworkInfo

        return activeNetwork?.isConnected == true
    }
}