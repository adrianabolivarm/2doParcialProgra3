package com.example.a2parciall.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para la respuesta de tasas de cambio de Frankfurter (API B).
 */
data class FrankfurterDto(
    @SerializedName("amount") val amount: Double? = 1.0,
    @SerializedName("base") val base: String? = "USD",
    @SerializedName("date") val date: String? = null,
    @SerializedName("rates") val rates: Map<String, Double>? = emptyMap()
)
