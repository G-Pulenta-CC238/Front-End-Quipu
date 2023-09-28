package com.gpulenta.quipu.shared

import com.gpulenta.quipu.dashboard.data.ProductData
import com.gpulenta.quipu.login.data.ApiResponse
import com.gpulenta.quipu.register.data.UserData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("user/byEmailAndPassword")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<ApiResponse>
    @POST("user")
    fun createUser(@Body userData: UserData): Call<Unit>
    @GET("products")
    suspend fun getProducts(): List<ProductData>
}