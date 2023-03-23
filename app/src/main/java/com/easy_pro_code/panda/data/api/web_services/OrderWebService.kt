package com.easy_pro_code.panda.data.api.web_services

import com.easy_pro_code.panda.data.Models.remote_backend.GetOrdersRequest
import com.easy_pro_code.panda.data.Models.remote_backend.GetOrdersResponse
import com.easy_pro_code.panda.data.Models.remote_backend.UserData
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import retrofit2.http.*

interface OrderWebService {

    @POST("allOrders")
    @Headers("authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=" , "token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NDFhNGFmMWMxZTU0N2NmNmMyNTE2Y2YiLCJpYXQiOjE2Nzk0OTg2NzAsImV4cCI6MTY3OTU4NTA3MH0.D0uhU1ftsgiDJvjTg576dE5LMsLpAX4efZNN0161LBw")
    suspend fun getOrders(@Body order:GetOrdersRequest) : GetOrdersResponse

}