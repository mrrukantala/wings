package com.example.myapplication.domain.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.myapplication.data.model.ProductItem
import com.example.myapplication.domain.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertProduct(productData: ProductItem):
            Flow<com.example.bossku.utils.common.base.Result<ProductEntity, ProductItem>>

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun selectAllProductDataByUser(key: String):
            Flow<com.example.bossku.utils.common.base.Result<List<ProductEntity>, List<ProductItem>>>

    suspend fun selectAllProductData(key: String):
            Flow<com.example.bossku.utils.common.base.Result<List<ProductEntity>, List<ProductItem>>>

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun selectAllProductDataId(key: String):
            Flow<com.example.bossku.utils.common.base.Result<ProductEntity, ProductItem>>
}