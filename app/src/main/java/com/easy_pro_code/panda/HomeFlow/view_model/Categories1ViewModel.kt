package com.easy_pro_code.panda.HomeFlow.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.CategoryResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.launch

class Categories1ViewModel:ViewModel() {
    val liveData= MutableLiveData<CategoryResponse>()
    val categoryWebService=ApiManager.getCategoryApi()
    fun getAllCategories(){
        viewModelScope.launch {
            try {
                liveData.value=categoryWebService.getAllCategories()
            }catch (ex:Exception){
                Log.i("error in product by category","error")
            }
        }
    }
}