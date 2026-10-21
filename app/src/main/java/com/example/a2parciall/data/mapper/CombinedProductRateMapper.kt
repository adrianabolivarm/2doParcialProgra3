package com.example.a2parciall.data.mapper

import com.example.a2parciall.data.remote.dto.DummyJsonResponseDto
import com.example.a2parciall.data.remote.dto.FrankfurterDto
import com.example.a2parciall.data.remote.dto.ProductDto
import com.example.a2parciall.model.ProductModel

/**
 * MAPPER COMBINADO 1:
 * Combina datos de API A (DummyJSON) y API B (Frankfurter).
 */
object CombinedProductRateMapper {

    fun map(dummyJsonDto: DummyJsonResponseDto, frankfurterDto: FrankfurterDto): List<ProductModel> {
        val eurRate = frankfurterDto.rates?.get("EUR") ?: 0.92
        val bobRate = frankfurterDto.rates?.get("BOB") ?: 6.96

        return dummyJsonDto.products.map { productDto ->
            mapSingleProduct(productDto, eurRate, bobRate)
        }
    }

    fun mapSingleProduct(
        productDto: ProductDto,
        eurRate: Double,
        bobRate: Double
    ): ProductModel {
        return ProductModel(
            id = productDto.id,
            title = productDto.title,
            description = productDto.description,
            price = productDto.price,
            category = productDto.category,
            thumbnail = productDto.thumbnail,
            priceEur = (productDto.price * eurRate),
            priceBob = (productDto.price * bobRate)
        )
    }
}
