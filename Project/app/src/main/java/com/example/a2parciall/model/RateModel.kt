package com.example.a2parciall.model

/**
 * Modelo de dominio para representar las tasas de cambio de divisas.
 */
data class RateModel(
    val baseCurrency: String,
    val eurRate: Double,
    val bobRate: Double
)
