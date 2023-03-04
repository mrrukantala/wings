package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.ProductItem
import com.example.myapplication.domain.repo.ProductRepository
import javax.inject.Inject

class ProductUserCase @Inject constructor(private val repo: ProductRepository) {
    suspend fun insertProduct(productData: ProductItem) = repo.insertProduct(productData)

    suspend fun selectAllProductDataByUser(key: String) = repo.selectAllProductDataByUser(key)

    suspend fun selectAllProductData(key: String) = repo.selectAllProductData(key)

    suspend fun selectAllProductDataId(key: String) = repo.selectAllProductDataId(key)
}