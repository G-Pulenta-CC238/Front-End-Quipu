package com.gpulenta.quipu.domain.model.request

data class TripRequest(
    val origin: String,
    val destination: String,
    val date: String,
    val userId: Long
)
