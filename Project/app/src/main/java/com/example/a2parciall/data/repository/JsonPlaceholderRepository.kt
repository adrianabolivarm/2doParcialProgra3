package com.example.a2parciall.data.repository

import com.example.a2parciall.data.remote.JsonPlaceholderApiService
import com.example.a2parciall.data.remote.ResultState
import com.example.a2parciall.data.remote.dto.JsonPlaceholderDto

class JsonPlaceholderRepository(
    private val apiService: JsonPlaceholderApiService
) {
    suspend fun getPosts(): ResultState<List<JsonPlaceholderDto>> {
        return try {
            val response = apiService.getPosts()
            ResultState.Success(response)
        } catch (e: Exception) {
            ResultState.Error(message = e.localizedMessage ?: "Error al obtener posts de JSONPlaceholder", throwable = e)
        }
    }
}
