package com.gpulenta.quipu.domain.model.response

data class ProductResponse(
    val id: Int,
    val productName: String,
    val productDescription: String,
    val productPrice: Double,
    val productImageUrl: String,
    val productRating: Int,
    val productStock: Int
)
