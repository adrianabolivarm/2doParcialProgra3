package com.example.a2parciall.data.repository

import com.example.a2parciall.data.local.ProductDao
import com.example.a2parciall.data.mapper.toDomainList
import com.example.a2parciall.data.mapper.toEntity
import com.example.a2parciall.model.ProductModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepository(
    private val productDao: ProductDao
) {
    val favorites: Flow<List<ProductModel>> = productDao.getAllFavorites().map { entities ->
        entities.toDomainList()
    }

    fun isFavorite(id: Int): Flow<Boolean> = productDao.isFavoriteFlow(id)

    suspend fun addFavorite(product: ProductModel) {
        productDao.insertFavorite(product.toEntity())
    }

    suspend fun deleteFavorite(id: Int) {
        productDao.deleteFavoriteById(id)
    }
}
