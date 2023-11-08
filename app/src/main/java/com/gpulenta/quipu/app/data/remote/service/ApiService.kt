package com.gpulenta.quipu.data.remote.service

import com.gpulenta.quipu.app.domain.model.request.Offer
import com.gpulenta.quipu.app.domain.model.response.OfferResponse
import com.gpulenta.quipu.app.domain.model.response.OfferStatusUpdate
import com.gpulenta.quipu.domain.model.request.CartItemData
import com.gpulenta.quipu.domain.model.request.LoginRequest
import com.gpulenta.quipu.domain.model.request.RegisterRequest
import com.gpulenta.quipu.domain.model.request.ShoppingCart
import com.gpulenta.quipu.domain.model.request.TripRequest
import com.gpulenta.quipu.domain.model.response.CartItemResponse
import com.gpulenta.quipu.domain.model.response.LoginResponse
import com.gpulenta.quipu.domain.model.response.ProductResponse
import com.gpulenta.quipu.domain.model.response.ShoppingCartResponse
import com.gpulenta.quipu.domain.model.response.Trip
import com.gpulenta.quipu.domain.model.response.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): LoginResponse

    @GET("product")
    suspend fun getProducts(): List<ProductResponse>

    @POST("shopping-cart")
    suspend fun createShoppingCart(@Body cartData: ShoppingCart)

    @GET("shopping-cart/by-user/{userId}")
    suspend fun getShoppingCartByUser(@Path("userId") userId: Long): ShoppingCartResponse

    @POST("cart-item")
    suspend fun createCartItem(@Body cartItemData: CartItemData): retrofit2.Response<Void>

    @GET("user/{id}")
    suspend fun getUserData(@Path("id") userId: Long): UserProfile

    @PUT("user/{id}")
    suspend fun updateUserData(@Path("id") userId: Long, @Body userData: UserProfile): UserProfile

    @GET("cart-item/by-shopping-cart/{id}")
    suspend fun getCartItemsByShoppingCart(@Path("id") shoppingCartId: Long): List<CartItemResponse>

    @DELETE("cart-item/by-id/{cartId}")
    suspend fun deleteCartItemById(@Path("cartId") cartId: Long): Response<Void>

    @POST("trip")
    suspend fun createTrip(@Body tripRequest: TripRequest): Response<Void>

    @GET("trip/by-user/{userId}")
    suspend fun getTripsByUser(@Path("userId") userId: Long): Response<List<Trip>>

    @POST("offer")
    suspend fun createOffer(@Body offer: Offer): Response<Void>

    @GET("offer")
    suspend fun getOffers(): List<OfferResponse>

    @PUT("offer/{id}")
    suspend fun updateOfferStatus(@Path("id") offerId: Long, @Body statusUpdate: OfferStatusUpdate): Response<Void>

}