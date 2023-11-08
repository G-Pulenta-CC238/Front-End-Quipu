package com.gpulenta.quipu.app.domain.model.request

data class Offer(
    val offerStatus: String,
    val offerPrice: Double,
    val userId: Long,
    val shoppingCartId: Long
)