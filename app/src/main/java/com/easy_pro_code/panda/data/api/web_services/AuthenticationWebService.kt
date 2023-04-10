package com.easy_pro_code.panda.data.api.web_services

import com.easy_pro_code.panda.data.Models.remote_backend.*
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthenticationWebService  {

    @Headers("Authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    @POST("signup")
    suspend fun signUp(@Body User: SignUpRequest ): SignUp

    @Headers("Authorization:Basic UIOIKMJOYWRtaW46cGFzc3dvcmQ=")
    @POST("login")
    suspend fun signIn(@Body user: SignInRequest): UserData
}