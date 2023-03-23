package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.GetAllProductsByCategoryRequest
import com.easy_pro_code.panda.data.Models.remote_backend.GetAllProductsByCategoryResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.launch

class CategoryViewModel:ViewModel() {
    val productsLiveData=MutableLiveData<GetAllProductsByCategoryResponse>()
    val productWebService=ApiManager.getProductApi()

    fun getProductByCategory(category:String){
        viewModelScope.launch {
            productsLiveData.value=productWebService.getAllProductsByCategory(
                GetAllProductsByCategoryRequest(category)
            )
        }
    }
}