package com.easy_pro_code.panda.data.api.web_services

import com.easy_pro_code.panda.data.Models.remote_backend.CreateAddressWithCacheRequest
import com.easy_pro_code.panda.data.Models.remote_backend.CreateAddressWithCacheResponse
import com.easy_pro_code.panda.data.Models.remote_backend.CreateAddressWithoutCacheRequest
import com.easy_pro_code.panda.data.Models.remote_backend.CreateAddresswithoutCacheResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CreateAddressWebService {
    @POST("user/Address")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun createAddressWithCache(@Body createAddressWithCache: CreateAddressWithCacheRequest):CreateAddressWithCacheResponse

    @POST("user/Address")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun createAddressWithoutCache(@Body createAddressWithoutCache: CreateAddressWithoutCacheRequest):CreateAddresswithoutCacheResponse
}