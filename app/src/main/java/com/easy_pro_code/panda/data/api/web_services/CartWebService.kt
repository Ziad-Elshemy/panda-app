package com.easy_pro_code.panda.data.api.web_services

import com.easy_pro_code.panda.HomeFlow.view_model.GetCartById
import com.easy_pro_code.panda.data.Models.remote_backend.AddCartRequest
import com.easy_pro_code.panda.data.Models.remote_backend.OrderCart
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import retrofit2.http.*

interface CartWebService {

    @POST("cart/product")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun addCart(
        @Body product : AddCartRequest,
        @Header("token")token:String?= AuthUtils.manager.getToken()
    ): OrderCart


    @GET("cart/product/{reqId}")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun getCarts(
        @Path("reqId") cardId:String = AuthUtils.manager.getCartId()!!,
       // @Path("reqId") cardId:String = "6419b093ac076d646e6a6ebe" ,
        @Header("x-access-token")token:String= AuthUtils.manager.getToken()?:""

    ) : GetCartById
}