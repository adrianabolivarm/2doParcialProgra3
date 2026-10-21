package com.example.a2parciall.data.repository

import com.example.a2parciall.data.local.PostDao
import com.example.a2parciall.data.mapper.toDomainList
import com.example.a2parciall.data.mapper.toEntity
import com.example.a2parciall.data.mapper.toEntityList
import com.example.a2parciall.data.remote.ApiService
import com.example.a2parciall.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repositorio que coordina las fuentes de datos (API remota y Room local).
 */
class PostRepository(
    private val apiService: ApiService,
    private val postDao: PostDao
) {

    /**
     * Expone la lista de posts almacenada en Room como un Flow de modelos puros de dominio.
     */
    val posts: Flow<List<Post>> = postDao.getAllPosts().map { entities ->
        entities.toDomainList()
    }

    /**
     * Refresca la información desde la red y la persiste en la base de datos local.
     */
    suspend fun refreshPosts(): Result<Unit> {
        return try {
            val remoteDtos = apiService.getPosts()
            postDao.insertPosts(remoteDtos.toEntityList())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun insertPost(post: Post) {
        postDao.insertPost(post.toEntity())
    }
}
