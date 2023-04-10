package com.easy_pro_code.panda.HomeFlow.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.CreateOrderRequest
import com.easy_pro_code.panda.data.Models.remote_backend.CreateOrderResponse
import com.easy_pro_code.panda.data.Models.remote_backend.GetOrdersRequest
import com.easy_pro_code.panda.data.Models.remote_backend.GetOrdersResponse
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import com.easy_pro_code.panda.data.api.web_services.OrderWebService
import kotlinx.coroutines.launch

class OrdersViewModel:ViewModel() {

    val orderLiveData:MutableLiveData<GetOrdersResponse?> = MutableLiveData<GetOrdersResponse?>()
    val createOrderLiveData:MutableLiveData<CreateOrderResponse?> = MutableLiveData<CreateOrderResponse?>()

    private val orderWebService:OrderWebService = ApiManager.getOrderApi()

    fun getAllOrders(){
        viewModelScope.launch {
            try {
                orderLiveData.value = orderWebService.getOrders(GetOrdersRequest(userId = AuthUtils.manager.fetchData().id.toString()))
                // AuthUtils.manager.fetchData().id
            }catch (ex:Exception){
                Log.i("error in product by category","error")
            }
        }
    }

    fun createOrder(){
        viewModelScope.launch {
            try {
                createOrderLiveData.value = orderWebService.createOrders(CreateOrderRequest(userId = AuthUtils.manager.fetchData().id.toString(),
                    cart = AuthUtils.manager.getCartId().toString()))
            }catch (e:Exception){
                Log.e("Ziad CreateOrder VM: ",e.message.toString())
            }
      }
    }

}