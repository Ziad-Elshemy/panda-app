package com.easy_pro_code.panda

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.easy_pro_code.panda.data.Models.remote_backend.SessionManager
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.data.local_database.app_database.AppDatabase

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        AuthUtils.manager= SessionManager.getInstance(applicationContext)
        AppDatabase.initialize(applicationContext)




        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                AuthUtils.networkFlag=true
            }

            // Network capabilities have changed for the network
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            }

            // lost network connection
            override fun onLost(network: Network) {
                AuthUtils.networkFlag=false
            }
        }

        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)

    }
}