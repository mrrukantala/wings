package com.example.myapplication.data.repositoryimpl

import android.util.Log
import com.example.bossku.utils.common.base.Result
import com.example.myapplication.data.model.TransactionDetailItem
import com.example.myapplication.data.model.TransactionHeaderItem
import com.example.myapplication.domain.dao.penjualan.TransactionDao
import com.example.myapplication.domain.entity.DetailTransactionEntity
import com.example.myapplication.domain.entity.ListTransactionEntity
import com.example.myapplication.domain.entity.TransactionDetailEntity
import com.example.myapplication.domain.entity.TransactionHeaderEntity
import com.example.myapplication.domain.repo.TransactionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TransactionRespositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {
    override suspend fun insertTransactionHeader(transactionHeader: TransactionHeaderItem): Flow<Result<TransactionHeaderEntity, TransactionHeaderItem>> {
        return flow {
            delay(1000)
            dao.insertHeader(transactionHeader)
            emit(Result.Success(transactionHeader.toTransactionHeaderEntity()))
        }
    }

    override suspend fun insertTransactionDetail(transactionDetailItem: TransactionDetailItem): Flow<Result<TransactionDetailEntity, TransactionDetailItem>> {
        return flow {
            delay(1000)
            dao.insertDetail(transactionDetailItem)
            emit(Result.Success(transactionDetailItem.toTransactionDetailEntity()))
        }
    }

    override suspend fun getListTransaction(key: String): Flow<Result<List<ListTransactionEntity>, List<ListTransactionEntity>>> {
        return flow {
            delay(800)
            val data = dao.getListTransaction(key)
            Log.v("DATA", "${data}")
            emit(Result.Success(data))
        }
    }

    override suspend fun getDetailTransaction(
        docCode: String,
        docNumber: String
    ): Flow<Result<DetailTransactionEntity, DetailTransactionEntity>> {
        return flow {
            delay(800)
            val data = dao.getDetailTransaction(docCode, docNumber)
            emit(Result.Success(data))
        }
    }
}