package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.AddCartRequest
import com.easy_pro_code.panda.data.Models.remote_backend.OrderCart
import com.easy_pro_code.panda.data.Models.remote_backend.OrderItemsItem
import com.easy_pro_code.panda.data.Models.remote_backend.ProductId
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.launch

class AddCartViewModel : ViewModel() {

    val cartsLiveData:MutableLiveData<OrderCart> = MutableLiveData<OrderCart>()

    private val cartWebService = ApiManager.AddCartApi()

    fun addToCart (userId : String , productId : String , number: Int){
        viewModelScope.launch {
            cartsLiveData.value = cartWebService.addCart(
                AddCartRequest(OrderCart(
                userId = userId ,
                    items = listOf( OrderItemsItem(
                        productId = ProductId() ,
                        number = number,
                    )
                    )
                ))
            )

        }
    }




}