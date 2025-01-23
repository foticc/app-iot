package com.foticc.iot.api

import com.foticc.iot.api.data.CommonResult
import com.foticc.iot.api.data.UserPassword
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/signin")
    suspend fun post(@Body user: UserPassword): CommonResult<String>
}