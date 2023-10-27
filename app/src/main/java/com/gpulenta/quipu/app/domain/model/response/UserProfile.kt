package com.gpulenta.quipu.domain.model.response

data class UserProfile(
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val address: String
)

