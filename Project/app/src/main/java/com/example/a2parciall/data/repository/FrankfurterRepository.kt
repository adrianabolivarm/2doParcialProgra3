package com.example.a2parciall.data.repository

import com.example.a2parciall.data.remote.FrankfurterApiService
import com.example.a2parciall.data.remote.ResultState
import com.example.a2parciall.data.remote.dto.FrankfurterDto

class FrankfurterRepository(
    private val apiService: FrankfurterApiService
) {
    suspend fun getRates(): ResultState<FrankfurterDto> {
        return try {
            val response = apiService.getRates()
            ResultState.Success(response)
        } catch (e: Exception) {
            // Estrategia de tolerancia a fallos: retornar tasas por defecto en caso de desconexión
            ResultState.Success(
                FrankfurterDto(
                    base = "USD",
                    rates = mapOf("EUR" to 0.92, "BOB" to 6.96)
                )
            )
        }
    }
}
