package com.example.a2parciall.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para la respuesta de publicaciones de JSONPlaceholder (API C).
 */
data class JsonPlaceholderDto(
    @SerializedName("userId") val userId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)
