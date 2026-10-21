package com.example.a2parciall.data.repository

import com.example.a2parciall.data.remote.DummyJsonApiService
import com.example.a2parciall.data.remote.ResultState
import com.example.a2parciall.data.remote.dto.DummyJsonResponseDto
import com.example.a2parciall.data.remote.dto.ProductDto

class DummyJsonRepository(
    private val apiService: DummyJsonApiService
) {
    suspend fun getProducts(): ResultState<DummyJsonResponseDto> {
        return try {
            val response = apiService.getProducts()
            ResultState.Success(response)
        } catch (e: Exception) {
            ResultState.Error(message = e.localizedMessage ?: "Error al obtener productos de DummyJSON", throwable = e)
        }
    }

    suspend fun getProductById(id: Int): ResultState<ProductDto> {
        return try {
            val product = apiService.getProductById(id)
            ResultState.Success(product)
        } catch (e: Exception) {
            ResultState.Error(message = e.localizedMessage ?: "Error al obtener el producto $id", throwable = e)
        }
    }
}
