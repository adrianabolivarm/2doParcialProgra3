package com.example.a2parciall.data.remote

import com.example.a2parciall.data.remote.dto.DummyJsonResponseDto
import com.example.a2parciall.data.remote.dto.ProductDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DummyJsonApiService {

    @GET("products")
    suspend fun getProducts(): DummyJsonResponseDto

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto

    companion object {
        const val BASE_URL = "https://dummyjson.com/"

        fun create(): DummyJsonApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DummyJsonApiService::class.java)
        }
    }
}
