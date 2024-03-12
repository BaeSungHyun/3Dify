package com.part2.a3dify.app_entry.uistates

data class RegisterRequest (
    val id: String, // shouldn't be null, required
    val email: String,
    val pwd: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val id: String
)

data class RegisterFragmentUiStates (
    val registerRequest: RegisterRequest? = null,
    val registerResponse: RegisterResponse?= null
)