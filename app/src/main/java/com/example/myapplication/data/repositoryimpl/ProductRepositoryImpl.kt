package com.example.myapplication.data.repositoryimpl

import android.util.Log
import com.example.bossku.utils.common.base.Result
import com.example.myapplication.data.model.ProductItem
import com.example.myapplication.domain.dao.penjualan.ProductDao
import com.example.myapplication.domain.entity.ProductEntity
import com.example.myapplication.domain.repo.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val dao: ProductDao) : ProductRepository {
    override suspend fun insertProduct(productData: ProductItem): Flow<Result<ProductEntity, ProductItem>> {
        return flow {
            delay(800)
            dao.insert(productData)
            emit(Result.Success(productData.toProductEntity()))
        }
    }

    override suspend fun selectAllProductDataByUser(key: String): Flow<Result<List<ProductEntity>, List<ProductItem>>> {
        return flow {
            delay(800)
            val data = dao.getListProductByUser(key)
            val result = data.map { it.toProductEntity() }
            emit(Result.Success(result ?: listOf()))
        }
    }

    override suspend fun selectAllProductData(key: String): Flow<Result<List<ProductEntity>, List<ProductItem>>> {
        return flow {
            delay(800)
            val data = dao.getAllProductData(key)
            val result = data.map { it.toProductEntity() }
            emit(Result.Success(result ?: listOf()))
        }
    }

    override suspend fun selectAllProductDataId(key: String): Flow<Result<ProductEntity, ProductItem>> {
        return flow {
            delay(800)
            val data = dao.getProductByCode(key).toProductEntity()
            emit(Result.Success(data))
        }
    }
}