package com.easy_pro_code.panda.data.api.web_services

import com.easy_pro_code.panda.data.Models.remote_backend.*
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import retrofit2.http.*

interface OrderWebService {

    @POST("allOrders")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun getOrders(@Body order:GetOrdersRequest,
                          @Header("x-access-token")token:String= AuthUtils.manager.getToken()?:""
    ) : GetOrdersResponse


    @POST("order")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun createOrders(@Body order: CreateOrderRequest,
                           @Header("x-access-token")token:String= AuthUtils.manager.getToken()?:""

    ) : CreateOrderResponse
}