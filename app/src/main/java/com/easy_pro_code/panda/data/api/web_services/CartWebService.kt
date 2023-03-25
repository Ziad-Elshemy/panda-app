package com.easy_pro_code.panda.data.api.web_services

import com.easy_pro_code.panda.data.Models.remote_backend.*
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import retrofit2.Response
import retrofit2.http.*

interface CartWebService {

    @POST("cart/product")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun addCart(
        @Body product : AddCartRequest,
        @Header("token")token:String?= AuthUtils.manager.getToken()
    ): AddCartResponse


    @GET("cart/product/{reqId}")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun getCarts(
        @Path("reqId") cardId:String = AuthUtils.manager.getCartId()!!,
//        @Path("reqId") cardId:String = "641cab173411912d4c4ce02d" ,
        @Header("x-access-token")token:String= AuthUtils.manager.getToken()?:"",
     //   @Body userId : UserIdRequest = UserIdRequest(AuthUtils.manager.fetchData().id)
    ) : GetCartById


    @PATCH("cart/{reqId}")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun updateCart(
        @Path("reqId") cardId:String = AuthUtils.manager.getCartId()!!,
        @Body updateCartRequest : UpdateCartRequest ,
        @Header("x-access-token")token:String= AuthUtils.manager.getToken()?:""
        ): UpdateCartResponse

}