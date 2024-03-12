package com.part2.a3dify.app_entry.repository
import com.part2.a3dify.app_entry.uistates.RegisterRequest
import com.part2.a3dify.app_entry.uistates.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterServiceInterface {
    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
}