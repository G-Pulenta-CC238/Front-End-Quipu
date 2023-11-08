package com.gpulenta.quipu.app.domain.model.response

data class Payment(
    val id: String,
    val paymentNumber: String,
    val paymentExpiration: String,
    val paymentCvv: String,
    val userId: Long
)

