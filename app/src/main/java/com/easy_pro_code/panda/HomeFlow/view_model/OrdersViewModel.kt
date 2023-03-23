package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.GetOrdersRequest
import com.easy_pro_code.panda.data.Models.remote_backend.GetOrdersResponse
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import com.easy_pro_code.panda.data.api.web_services.OrderWebService
import kotlinx.coroutines.launch

class OrdersViewModel:ViewModel() {

    val orderLiveData:MutableLiveData<GetOrdersResponse?> = MutableLiveData<GetOrdersResponse?>()

    private val orderWebService:OrderWebService = ApiManager.getOrderApi()

    fun getAllOrders(){
        viewModelScope.launch {
            orderLiveData.value = orderWebService.getOrders(GetOrdersRequest(userId ="641a4af1c1e547cf6c2516cf"))
            // AuthUtils.manager.fetchData().id
        }
    }

}