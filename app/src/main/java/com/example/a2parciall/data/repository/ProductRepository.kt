package com.example.a2parciall.data.repository

import com.example.a2parciall.data.mapper.CombinedProductRateMapper
import com.example.a2parciall.data.mapper.CombinedRatePostMapper
import com.example.a2parciall.data.remote.ResultState
import com.example.a2parciall.data.remote.dto.FrankfurterDto
import com.example.a2parciall.model.PostModel
import com.example.a2parciall.model.ProductModel

class ProductRepository(
    private val dummyJsonRepository: DummyJsonRepository,
    private val frankfurterRepository: FrankfurterRepository,
    private val jsonPlaceholderRepository: JsonPlaceholderRepository
) {

    suspend fun getCombinedMarketProducts(): ResultState<List<ProductModel>> {
        val productsResult = dummyJsonRepository.getProducts()
        val ratesResult = frankfurterRepository.getRates()

        if (productsResult is ResultState.Error) {
            return ResultState.Error(productsResult.message, productsResult.throwable)
        }

        val dummyJsonDto = (productsResult as ResultState.Success).data
        val frankfurterDto = when (ratesResult) {
            is ResultState.Success -> ratesResult.data
            else -> FrankfurterDto(base = "USD", rates = mapOf("EUR" to 0.92, "BOB" to 6.96))
        }

        val combinedList = CombinedProductRateMapper.map(dummyJsonDto, frankfurterDto)
        return ResultState.Success(combinedList)
    }

    suspend fun getCombinedMarketPosts(): ResultState<List<PostModel>> {
        val postsResult = jsonPlaceholderRepository.getPosts()
        val ratesResult = frankfurterRepository.getRates()

        if (postsResult is ResultState.Error) {
            return ResultState.Error(postsResult.message, postsResult.throwable)
        }

        val postsDto = (postsResult as ResultState.Success).data
        val frankfurterDto = when (ratesResult) {
            is ResultState.Success -> ratesResult.data
            else -> FrankfurterDto(base = "USD", rates = mapOf("EUR" to 0.92, "BOB" to 6.96))
        }

        val combinedPosts = CombinedRatePostMapper.map(frankfurterDto, postsDto)
        return ResultState.Success(combinedPosts)
    }
}
