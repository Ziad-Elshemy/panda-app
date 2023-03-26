package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationData:ViewModel() {

    var LiveData:String = "Not Found"

    fun setLacation(AdressLocation:String){
        LiveData = AdressLocation
    }
    fun getlocation():String{
        return LiveData
    }
}