package com.example.a2parciall.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2parciall.data.local.AppDatabase
import com.example.a2parciall.data.repository.FavoritesRepository
import com.example.a2parciall.model.ProductModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface FavoritesUiState {
    object Empty : FavoritesUiState
    data class Success(val favorites: List<ProductModel>) : FavoritesUiState
}

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesRepository: FavoritesRepository

    val uiState: StateFlow<FavoritesUiState>

    init {
        val database = AppDatabase.getDatabase(application)
        favoritesRepository = FavoritesRepository(database.productDao())

        uiState = favoritesRepository.favorites.map { list ->
            if (list.isEmpty()) FavoritesUiState.Empty else FavoritesUiState.Success(list)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState.Empty
        )
    }

    fun toggleFavorite(product: ProductModel, isCurrentFavorite: Boolean) {
        viewModelScope.launch {
            if (isCurrentFavorite) {
                favoritesRepository.deleteFavorite(product.id)
            } else {
                favoritesRepository.addFavorite(product)
            }
        }
    }

    fun deleteFavorite(id: Int) {
        viewModelScope.launch {
            favoritesRepository.deleteFavorite(id)
        }
    }

    fun isFavorite(id: Int) = favoritesRepository.isFavorite(id)
}
