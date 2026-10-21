package com.example.a2parciall.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de Room para la tabla de productos guardados en favoritos.
 */
@Entity(tableName = "favorite_products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val thumbnail: String,
    val priceEur: Double,
    val priceBob: Double
)
