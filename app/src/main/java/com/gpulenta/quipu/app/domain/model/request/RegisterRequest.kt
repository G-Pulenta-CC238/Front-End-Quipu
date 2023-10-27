package com.gpulenta.quipu.domain.model.request

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val address: String
)
