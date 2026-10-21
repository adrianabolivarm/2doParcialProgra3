package com.example.a2parciall.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para la respuesta de productos de DummyJSON (API A).
 */
data class DummyJsonResponseDto(
    @SerializedName("products") val products: List<ProductDto>
)

data class ProductDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("category") val category: String,
    @SerializedName("thumbnail") val thumbnail: String
)
