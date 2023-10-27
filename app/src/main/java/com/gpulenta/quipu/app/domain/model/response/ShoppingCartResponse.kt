package com.gpulenta.quipu.domain.model.response

data class ShoppingCartResponse(
    val id: Long,
    val cartStatus: String,
    val userId: Long
)

