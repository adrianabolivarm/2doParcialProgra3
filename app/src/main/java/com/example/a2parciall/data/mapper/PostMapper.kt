package com.example.a2parciall.data.mapper

import com.example.a2parciall.data.local.PostEntity
import com.example.a2parciall.data.remote.dto.PostDto
import com.example.a2parciall.model.Post

/**
 * Extensiones Kotlin para mapear DTOs/Entities a Modelos limpios de dominio y viceversa.
 */

// De DTO a Modelo de Dominio
fun PostDto.toDomain(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

// De DTO a Entidad de Room
fun PostDto.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

// De Entidad a Modelo de Dominio
fun PostEntity.toDomain(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

// De Modelo de Dominio a Entidad de Room
fun Post.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

fun List<PostDto>.toEntityList(): List<PostEntity> = map { it.toEntity() }
fun List<PostEntity>.toDomainList(): List<Post> = map { it.toDomain() }
