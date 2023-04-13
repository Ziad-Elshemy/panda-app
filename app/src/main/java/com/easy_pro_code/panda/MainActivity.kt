package com.easy_pro_code.panda

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var networkRequest: NetworkRequest
    lateinit var networkCallback: ConnectivityManager.NetworkCallback
    lateinit var connectivityManager: ConnectivityManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
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
                lifecycleScope.launch(Dispatchers.Main){
                    showSettingsAlert()
                }
            }
        }

        connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
        if (!AuthUtils.networkFlag){
            showSettingsAlert()
        }

    }

    fun showSettingsAlert() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)

        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.error))

        // Setting Dialog Message
        alertDialog.setMessage(getString(R.string.please_check_interent))

        // On pressing Settings button
        alertDialog.setPositiveButton(
            getString(R.string.try_again),
            DialogInterface.OnClickListener { dialog, which ->
                if (!AuthUtils.networkFlag){
                    showSettingsAlert()
                }
            }).setNegativeButton(
            getString(R.string.close_app),
            DialogInterface.OnClickListener { dialog, which ->
                finish()
            }

        )
        alertDialog.show()
    }


}