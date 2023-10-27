package com.gpulenta.quipu.domain.model.request

data class CartItemData(
    val productQuantity: Int,
    val cartSubtotal: Double,
    val productId: Int,
    val shoppingCart: ShoppingCarta
)

data class ShoppingCarta(
    val id: Long
)

