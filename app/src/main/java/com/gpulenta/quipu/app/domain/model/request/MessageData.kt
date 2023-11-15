package com.gpulenta.quipu.app.domain.model.request

data class MessageData(
    val message: String,
    val userId: Long,
    val offerId: Long
)

