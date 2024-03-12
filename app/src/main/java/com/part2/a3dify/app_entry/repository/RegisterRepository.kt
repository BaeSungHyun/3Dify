package com.part2.a3dify.app_entry.repository

import com.part2.a3dify.app_entry.uistates.RegisterRequest
import com.part2.a3dify.app_entry.uistates.RegisterResponse
import retrofit2.Response

class RegisterRepository(private val apiService: RegisterServiceInterface) {
    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return apiService.registerUser(registerRequest)
    }
}