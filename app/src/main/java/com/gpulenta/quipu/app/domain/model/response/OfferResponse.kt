package com.gpulenta.quipu.app.domain.model.response

data class OfferResponse(
    val id: Long,
    val offerStatus: String,
    val offerPrice: Double,
    val userId: Long,
    val user: Any?,
    val shoppingCartId: Long
)
