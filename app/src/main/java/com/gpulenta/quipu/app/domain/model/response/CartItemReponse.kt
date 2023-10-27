package com.gpulenta.quipu.domain.model.response

data class CartItemResponse(
    val id: Long,
    val productQuantity: Int,
    val cartSubtotal: Double,
    val productId: Int,
    val product: ProductResponse,
    val shoppingCart: ShoppingCartResponses
)
data class ShoppingCartResponses(
    val id: Int,
    val cartStatus: String,
    val userId: Int,
    val user: Any? // Esto depende de la estructura real de "user" en la respuesta
)

