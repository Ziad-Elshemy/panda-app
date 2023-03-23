package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.AddCartRequest
import com.easy_pro_code.panda.data.Models.remote_backend.Cart
import com.easy_pro_code.panda.data.Models.remote_backend.ProductItemsItem
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.launch

class AddCartViewModel : ViewModel() {

    val cartsLiveData:MutableLiveData<Cart> = MutableLiveData<Cart>()

    private val cartWebService = ApiManager.getAllCartApi()

    fun addToCart (userId : String , productId : String , number: Int){
        viewModelScope.launch {
//            cartsLiveData.value = cartWebService.addCart(
//                AddCartRequest(,)
//            )

        }
    }




}