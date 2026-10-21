package com.example.a2parciall.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO de Room con métodos reactivos (Flow) y suspend para inserción y eliminación mediante @Query.
 */
@Dao
interface ProductDao {

    @Query("SELECT * FROM favorite_products")
    fun getAllFavorites(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM favorite_products WHERE id = :id")
    suspend fun getFavoriteById(id: Int): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(product: ProductEntity)

    @Query("DELETE FROM favorite_products WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_products WHERE id = :id)")
    fun isFavoriteFlow(id: Int): Flow<Boolean>
}
