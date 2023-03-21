package com.easy_pro_code.panda.HomeFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController

import com.easy_pro_code.panda.HomeFlow.view_model.SuspendWindowViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private val suspendWindowViewMode: SuspendWindowViewModel by viewModels()
    lateinit var dataBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        subscribeToLiveData()


        val navController = Navigation.findNavController(this,R.id.fragmentContainerView)
        dataBinding.bottomNav.setupWithNavController(navController)
    }


    private fun subscribeToLiveData() {
        suspendWindowViewMode.suspendWindowLiveData.observe(this){
            it?.let{
                if (it){
                    showSuspendWindow()
                }else{
                    hideSuspendWindow()
                }
//                suspendWindowViewMode.suspendWindowLiveData.value=null
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
}