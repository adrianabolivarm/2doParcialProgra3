package com.example.a2parciall.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2parciall.data.local.AppDatabase
import com.example.a2parciall.data.remote.ApiService
import com.example.a2parciall.data.repository.PostRepository
import com.example.a2parciall.model.Post
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Representa los estados UI de la pantalla.
 */
sealed interface UiState<out T> {
    object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}

/**
 * ViewModel que hereda de AndroidViewModel(application) y expone StateFlow
 * utilizando corrutinas (viewModelScope) y la función combine.
 */
class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<UiState<List<Post>>>

    init {
        val database = AppDatabase.getDatabase(application)
        val apiService = ApiService.create()
        repository = PostRepository(apiService, database.postDao())

        uiState = combine(
            repository.posts,
            _searchQuery,
            _errorMessage
        ) { posts, query, error ->
            if (error != null && posts.isEmpty()) {
                UiState.Error(error)
            } else {
                val filteredPosts = if (query.isBlank()) {
                    posts
                } else {
                    posts.filter {
                        it.title.contains(query, ignoreCase = true) ||
                                it.body.contains(query, ignoreCase = true)
                    }
                }
                UiState.Success(filteredPosts)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

        refreshPosts()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun refreshPosts() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _errorMessage.value = null
            val result = repository.refreshPosts()
            result.onFailure { exception ->
                _errorMessage.value = exception.localizedMessage ?: "Error al conectar con el servidor"
            }
            _isRefreshing.value = false
        }
    }
}
