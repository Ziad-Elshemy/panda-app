package com.easy_pro_code.panda.HomeFlow.presentations.Review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewViewModel : ViewModel() {

    private val navigationToreviewLiveData =MutableLiveData<Boolean>()
     val _navigationToreviewLiveData : LiveData<Boolean> = navigationToreviewLiveData


    fun navigateToReview(){
        navigationToreviewLiveData.value=true
    }


    fun onNavigateToReview(){
        navigationToreviewLiveData.value=false
    }



}