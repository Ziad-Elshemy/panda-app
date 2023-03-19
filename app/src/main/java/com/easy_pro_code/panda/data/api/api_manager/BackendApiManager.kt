package com.easy_pro_code.panda.data.api.api_manager

import com.easy_pro_code.panda.data.api.web_services.AuthenticationWebService
import com.sendbird.android.shadow.okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager {

    companion object {
        private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BASIC
        )
        private val client: okhttp3.OkHttpClient = okhttp3.OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()


        private const val BACKEND_URL = "http://207.180.239.207:8090/api/"
        private lateinit var retrofit: Retrofit
        private fun getInstance(): Retrofit {
            synchronized(this) {
                if (!Companion::retrofit.isInitialized) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BACKEND_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
                }
                return retrofit
            }
        }


        fun getAuthenticationApi(): AuthenticationWebService {
            return getInstance().create(AuthenticationWebService::class.java)
        }
    }
}