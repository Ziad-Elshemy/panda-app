package com.easy_pro_code.panda

import android.app.Application
import com.easy_pro_code.panda.data.Models.remote_backend.SessionManager
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        AuthUtils.manager= SessionManager.getInstance(applicationContext)
    }
}