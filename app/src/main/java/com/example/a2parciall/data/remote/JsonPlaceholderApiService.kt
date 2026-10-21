package com.example.a2parciall.data.remote

import com.example.a2parciall.data.remote.dto.JsonPlaceholderDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface JsonPlaceholderApiService {

    @GET("posts")
    suspend fun getPosts(): List<JsonPlaceholderDto>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): JsonPlaceholderApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JsonPlaceholderApiService::class.java)
        }
    }
}
