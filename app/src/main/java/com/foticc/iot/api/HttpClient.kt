package com.foticc.iot.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClient {

    companion object{

        private const val BASE_URL = "http://192.168.160.138:3000"
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun <T> create(clazz: Class<T>): T {
            return retrofit.create(clazz) as T
        }
    }
}