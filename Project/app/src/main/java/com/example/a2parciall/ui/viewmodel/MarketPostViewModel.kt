package com.example.a2parciall.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2parciall.data.remote.DummyJsonApiService
import com.example.a2parciall.data.remote.FrankfurterApiService
import com.example.a2parciall.data.remote.JsonPlaceholderApiService
import com.example.a2parciall.data.remote.ResultState
import com.example.a2parciall.data.repository.DummyJsonRepository
import com.example.a2parciall.data.repository.FrankfurterRepository
import com.example.a2parciall.data.repository.JsonPlaceholderRepository
import com.example.a2parciall.data.repository.ProductRepository
import com.example.a2parciall.model.PostModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MarketPostUiState {
    object Loading : MarketPostUiState
    data class Success(val posts: List<PostModel>) : MarketPostUiState
    data class Error(val message: String) : MarketPostUiState
}

class MarketPostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository

    private val _uiState = MutableStateFlow<MarketPostUiState>(MarketPostUiState.Loading)
    val uiState: StateFlow<MarketPostUiState> = _uiState.asStateFlow()

    init {
        val dummyRepo = DummyJsonRepository(DummyJsonApiService.create())
        val frankfurterRepo = FrankfurterRepository(FrankfurterApiService.create())
        val jsonPlaceholderRepo = JsonPlaceholderRepository(JsonPlaceholderApiService.create())
        repository = ProductRepository(dummyRepo, frankfurterRepo, jsonPlaceholderRepo)

        loadMarketPosts()
    }

    fun loadMarketPosts() {
        viewModelScope.launch {
            _uiState.value = MarketPostUiState.Loading
            when (val result = repository.getCombinedMarketPosts()) {
                is ResultState.Success -> {
                    _uiState.value = MarketPostUiState.Success(result.data)
                }
                is ResultState.Error -> {
                    _uiState.value = MarketPostUiState.Error(result.message)
                }
                is ResultState.Loading -> {
                    _uiState.value = MarketPostUiState.Loading
                }
            }
        }
    }
}
