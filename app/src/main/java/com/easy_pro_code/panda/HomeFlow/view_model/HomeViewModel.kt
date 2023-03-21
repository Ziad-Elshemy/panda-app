package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.CategoryResponse
import com.easy_pro_code.panda.data.Models.remote_backend.GetAllProductsResponse
import com.easy_pro_code.panda.data.Models.remote_backend.OffersResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {

    val productsLiveData:MutableLiveData<GetAllProductsResponse> = MutableLiveData<GetAllProductsResponse>()
    val offersLiveData:MutableLiveData<OffersResponse> =MutableLiveData()
    val categoryLiveData:MutableLiveData<CategoryResponse> =MutableLiveData()
    private val offersWebService=ApiManager.getOfferApi()
    private val productWebService=ApiManager.getProductApi()
    private val categoryWebService=ApiManager.getCategoryApi()

    fun getAllProducts(){
        viewModelScope.launch {
            productsLiveData.value=productWebService.getAllProducts()
        }
    }

    fun getAllOffers(){
        viewModelScope.launch {
            offersLiveData.value=offersWebService.getOffers()
        }
    }

    fun getAllCategories(){
        viewModelScope.launch {
            categoryLiveData.value=categoryWebService.getAllCategories()
        }
    }


}