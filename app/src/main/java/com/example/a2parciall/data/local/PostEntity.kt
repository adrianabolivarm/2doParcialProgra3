package com.example.a2parciall.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de Room (@Entity) que representa la tabla 'posts' en SQLite.
 */
@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
