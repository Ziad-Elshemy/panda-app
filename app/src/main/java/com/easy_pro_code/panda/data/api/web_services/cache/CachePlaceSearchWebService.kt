package com.easy_pro_code.panda.data.api.web_services.cache

import com.easy_pro_code.panda.data.Models.remote_backend.CachePlaceSearchRequest
import com.easy_pro_code.panda.data.Models.remote_backend.CachePlaceSearchResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CachePlaceSearchWebService {
    @POST("GetAddress")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun placeSearch(@Body location:CachePlaceSearchRequest):CachePlaceSearchResponse
}