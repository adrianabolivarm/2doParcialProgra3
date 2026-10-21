package com.example.a2parciall.data.remote

import com.example.a2parciall.data.remote.dto.FrankfurterDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FrankfurterApiService {

    @GET("v2/rates")
    suspend fun getRates(
        @Query("base") base: String = "USD",
        @Query("quotes") quotes: String = "EUR,BOB"
    ): FrankfurterDto

    companion object {
        const val BASE_URL = "https://api.frankfurter.dev/"

        fun create(): FrankfurterApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FrankfurterApiService::class.java)
        }
    }
}
