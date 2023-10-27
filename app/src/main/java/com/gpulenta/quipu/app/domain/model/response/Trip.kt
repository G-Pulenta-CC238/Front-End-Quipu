package com.gpulenta.quipu.domain.model.response

import com.google.gson.annotations.SerializedName

data class Trip(
    val id: Long,
    val origin: String,
    val destination: String,
    val date: String,
    val userId: Long
)
