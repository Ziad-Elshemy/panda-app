package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.*
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.launch

class AddCartViewModel : ViewModel() {

    val cartsLiveData:MutableLiveData<AddCartResponse> = MutableLiveData<AddCartResponse>()
    val updateCartLiveDate:MutableLiveData<UpdateCartResponse> = MutableLiveData<UpdateCartResponse>()

    private val cartWebService = ApiManager.AddCartApi()

    fun addToCart (userId: String, productId: String, number: Int){
        viewModelScope.launch {
            cartsLiveData.value = cartWebService.addCart(
                AddCartRequest(
                    userId = userId ,
                    items = listOf(ItemsItemResponse(
                    productId = productId ,
                    number = number
                ))
                )
            )
        }
    }

    fun updateCart (number: Int , productId: String){
        viewModelScope.launch {
            updateCartLiveDate.value =cartWebService.updateCart(updateCartRequest = UpdateCartRequest(productId,number))
        }

    }



}