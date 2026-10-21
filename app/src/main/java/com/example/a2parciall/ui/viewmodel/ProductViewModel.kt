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
import com.example.a2parciall.model.ProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProductUiState {
    object Loading : ProductUiState
    data class Success(val products: List<ProductModel>) : ProductUiState
    data class Error(val message: String) : ProductUiState
}

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedProduct = MutableStateFlow<ProductModel?>(null)
    val selectedProduct: StateFlow<ProductModel?> = _selectedProduct.asStateFlow()

    init {
        val dummyRepo = DummyJsonRepository(DummyJsonApiService.create())
        val frankfurterRepo = FrankfurterRepository(FrankfurterApiService.create())
        val jsonPlaceholderRepo = JsonPlaceholderRepository(JsonPlaceholderApiService.create())
        repository = ProductRepository(dummyRepo, frankfurterRepo, jsonPlaceholderRepo)

        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            when (val result = repository.getCombinedMarketProducts()) {
                is ResultState.Success -> {
                    _uiState.value = ProductUiState.Success(result.data)
                }
                is ResultState.Error -> {
                    _uiState.value = ProductUiState.Error(result.message)
                }
                is ResultState.Loading -> {
                    _uiState.value = ProductUiState.Loading
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun selectProduct(product: ProductModel?) {
        _selectedProduct.value = product
    }
}
