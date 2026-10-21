package com.example.a2parciall.model

/**
 * Modelo de dominio para representar un producto en la capa UI,
 * enriquecido con precios convertidos a EUR y BOB.
 */
data class ProductModel(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val thumbnail: String,
    val priceEur: Double = 0.0,
    val priceBob: Double = 0.0
)
