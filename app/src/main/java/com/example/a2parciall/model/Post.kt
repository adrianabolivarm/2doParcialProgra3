package com.example.a2parciall.model

/**
 * Modelo de dominio puro. No depende de Room ni de Gson.
 */
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
