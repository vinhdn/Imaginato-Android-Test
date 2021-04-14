package com.example.androidtest.data.remote

import com.example.androidtest.data.entities.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun login(
        @Body data: Map<String, String>,
        @Header("IMSI") imsi: String = "357175048449937",
        @Header("IMEI") imei: String = "510110406068589"
    ): Response<LoginResponse?>
}