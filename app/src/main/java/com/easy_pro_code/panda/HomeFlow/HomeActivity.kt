package com.easy_pro_code.panda.HomeFlow

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope

import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.easy_pro_code.panda.AuthFlow.splash.SplashActivity

import com.easy_pro_code.panda.HomeFlow.view_model.SuspendWindowViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.ActivityHomeBinding
import com.easy_pro_code.panda.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {
    private val suspendWindowViewMode: SuspendWindowViewModel by viewModels()
    lateinit var dataBinding: ActivityHomeBinding

    lateinit var networkRequest: NetworkRequest
    lateinit var networkCallback: ConnectivityManager.NetworkCallback
    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        val navController = Navigation.findNavController(this,R.id.fragmentContainerView)
        dataBinding.bottomNav.setupWithNavController(navController)


        subscribeToLiveData()


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



//        if (checkForInternet(this)) {
//            Toast.makeText(this, "Welcome To Panda", Toast.LENGTH_SHORT).show()
//        } else {
//            val intent = Intent(this, SplashActivity::class.java)
//            startActivity(intent)
//            this.finish()
//            Toast.makeText(this, "Check your network", Toast.LENGTH_SHORT).show()
//        }


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

    private fun subscribeToLiveData() {
        suspendWindowViewMode.suspendWindowLiveData.observe(this){
            it?.let{
                if (it){
                    showSuspendWindow()
                }else{
                    hideSuspendWindow()
                }
            }
        }
    }

    private fun showSuspendWindow() {
        dataBinding.greyBackground.visibility = View.VISIBLE
        dataBinding.progressBar.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }



    private fun hideSuspendWindow(){
        dataBinding.greyBackground.visibility = View.GONE
        dataBinding.progressBar.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }



    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


}