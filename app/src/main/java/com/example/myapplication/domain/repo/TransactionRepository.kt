package com.example.myapplication.domain.repo

import androidx.annotation.WorkerThread
import com.example.bossku.utils.common.base.Result
import com.example.myapplication.data.model.TransactionDetailItem
import com.example.myapplication.data.model.TransactionHeaderItem
import com.example.myapplication.domain.entity.DetailTransactionEntity
import com.example.myapplication.domain.entity.ListTransactionEntity
import com.example.myapplication.domain.entity.TransactionDetailEntity
import com.example.myapplication.domain.entity.TransactionHeaderEntity
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insertTransactionHeader(transactionHeader: TransactionHeaderItem):
            Flow<Result<TransactionHeaderEntity, TransactionHeaderItem>>

    suspend fun insertTransactionDetail(transactionDetailItem: TransactionDetailItem):
            Flow<Result<TransactionDetailEntity, TransactionDetailItem>>

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getListTransaction(key: String):
            Flow<Result<List<ListTransactionEntity>, List<ListTransactionEntity>>>

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getDetailTransaction(docCode: String, docNumber: String):
            Flow<Result<DetailTransactionEntity, DetailTransactionEntity>>
}