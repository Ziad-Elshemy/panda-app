package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.GetAllProductsResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {

    val productsLiveData:MutableLiveData<GetAllProductsResponse> = MutableLiveData<GetAllProductsResponse>()

    private val productWebService=ApiManager.getProductApi()

    fun getAllProducts(){
        viewModelScope.launch {
            productsLiveData.value=productWebService.getAllProducts()
        }
    }


}