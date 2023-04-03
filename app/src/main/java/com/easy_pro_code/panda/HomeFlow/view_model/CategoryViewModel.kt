package com.easy_pro_code.panda.HomeFlow.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.GetAllProductsByCategoryRequest
import com.easy_pro_code.panda.data.Models.remote_backend.GetAllProductsByCategoryResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.launch

class CategoryViewModel:ViewModel() {
    val productsLiveData=MutableLiveData<GetAllProductsByCategoryResponse>()
    val phonesProductsLiveData=MutableLiveData<GetAllProductsByCategoryResponse>()
    val electronicsProductsLiveData=MutableLiveData<GetAllProductsByCategoryResponse>()
    private val productWebService=ApiManager.getProductApi()

    fun getProductByCategory(category:String){
        viewModelScope.launch {
            try {
                productsLiveData.value=productWebService.getAllProductsByCategory(
                    GetAllProductsByCategoryRequest(category)
                )
            }catch (ex:Exception){
                Log.i("error in product by category","error")
            }
        }
    }

    fun getPhoneProductByCategory(category:String){
        viewModelScope.launch {
            try {
                phonesProductsLiveData.value=productWebService.getAllProductsByCategory(
                    GetAllProductsByCategoryRequest(category)
                )
            }catch (ex:Exception){
                Log.i("error in product by category","error")
            }
        }
    }

    fun getElectronicProductByCategory(category:String){
        viewModelScope.launch {
            try {
                electronicsProductsLiveData.value=productWebService.getAllProductsByCategory(
                    GetAllProductsByCategoryRequest(category)
                )
            }catch (ex:Exception){
                Log.i("error in product by category","error")
            }
        }
    }
}