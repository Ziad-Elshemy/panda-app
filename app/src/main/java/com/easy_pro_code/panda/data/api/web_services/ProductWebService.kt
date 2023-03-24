package com.easy_pro_code.panda.data.api.web_services

import com.easy_pro_code.panda.data.Models.remote_backend.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ProductWebService {

    @GET("products")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun getAllProducts():GetAllProductsResponse

    @POST("products/category")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun getAllProductsByCategory(@Body category: GetAllProductsByCategoryRequest):GetAllProductsByCategoryResponse

    @POST("products/search")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    suspend fun search(@Body searchValue: SearchRequest):SearchResponse

}