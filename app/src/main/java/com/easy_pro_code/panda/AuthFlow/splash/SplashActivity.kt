package com.easy_pro_code.panda.AuthFlow.splash

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.easy_pro_code.panda.AuthFlow.Model.LoginViewModel
import com.easy_pro_code.panda.HomeFlow.HomeActivity
import com.easy_pro_code.panda.MainActivity
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)

        // Handler().postDelayed({
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,Start()::class.java)
            startActivity(intent)
            finish()
        }, 3000)// 3000 is the delayed time in milliseconds.





    }
    private fun Start():Activity
    {
        with(loginViewModel)
        {
            val user = autoSignIn()

            user.token?.let {
                sessionManager.isTokenExpired()?.let {
                    if (it) {
                        loginViewModel.sessionManager.getPhone()?.let { phoneNumber ->
                            loginViewModel.logIn(phoneNumber)
                        }
                    }
                }

            }
            Log.i("tokennnnnnnnn",sessionManager.getToken().toString() )
            if(sessionManager.getToken()!=null)
            {
                return HomeActivity()
            }
            else{

                return MainActivity()

            }

        }


    }


}