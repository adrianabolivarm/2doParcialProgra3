package com.example.a2parciall.model

/**
 * Modelo de dominio para representar una publicación combinada con las
 * tasas de cambio del mercado actual (Mapper 2).
 */
data class PostModel(
    val id: Int,
    val title: String,
    val body: String,
    val marketRatesSummary: String
)
