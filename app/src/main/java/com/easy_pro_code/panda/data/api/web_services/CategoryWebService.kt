package com.easy_pro_code.panda.data.api.web_services

import com.easy_pro_code.panda.data.Models.remote_backend.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface CategoryWebService {
    @GET("category")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun getAllCategories():CategoryResponse
}