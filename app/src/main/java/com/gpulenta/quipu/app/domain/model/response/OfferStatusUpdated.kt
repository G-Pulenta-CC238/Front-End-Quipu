package com.gpulenta.quipu.app.domain.model.response

data class OfferStatusUpdate(
    val id: Long, // Agrega el ID de la oferta que quieres actualizar
    val offerStatus: String, // Agrega los campos que necesites para actualizar el estado
    val offerPrice: Double,
    val userId: Long,
    val shoppingCartId: Long
)
