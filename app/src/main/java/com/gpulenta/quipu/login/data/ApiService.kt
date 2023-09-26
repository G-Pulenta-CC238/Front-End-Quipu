package com.gpulenta.quipu.login.data

import com.gpulenta.quipu.login.data.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("user/byEmailAndPassword")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<ApiResponse>
}