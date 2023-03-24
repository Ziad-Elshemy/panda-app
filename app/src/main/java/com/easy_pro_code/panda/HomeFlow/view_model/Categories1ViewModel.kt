package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.CategoryItem
import com.easy_pro_code.panda.data.Models.remote_backend.CategoryResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.launch

class Categories1ViewModel:ViewModel() {
    val liveData= MutableLiveData<CategoryResponse>()
    val categoryWebService=ApiManager.getCategoryApi()
    fun getAllCategories(){
        viewModelScope.launch {
            liveData.value=categoryWebService.getAllCategories()
        }
    }
}