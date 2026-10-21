package com.example.a2parciall.data.mapper

import com.example.a2parciall.data.local.ProductEntity
import com.example.a2parciall.model.ProductModel

fun ProductModel.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        thumbnail = thumbnail,
        priceEur = priceEur,
        priceBob = priceBob
    )
}

fun ProductEntity.toDomain(): ProductModel {
    return ProductModel(
        id = id,
        title = title,
        description = description,
        price = price,
        category = category,
        thumbnail = thumbnail,
        priceEur = priceEur,
        priceBob = priceBob
    )
}

fun List<ProductEntity>.toDomainList(): List<ProductModel> = map { it.toDomain() }
