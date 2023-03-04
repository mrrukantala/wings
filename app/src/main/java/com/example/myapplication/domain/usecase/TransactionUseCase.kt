package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.TransactionDetailItem
import com.example.myapplication.data.model.TransactionHeaderItem
import com.example.myapplication.domain.repo.TransactionRepository
import javax.inject.Inject

class TransactionUseCase @Inject constructor(private val repo: TransactionRepository) {
    suspend fun insertTransactionHeader(transactionHeader: TransactionHeaderItem) =
        repo.insertTransactionHeader(transactionHeader)

    suspend fun insertTransactionDetail(transactionDetailItem: TransactionDetailItem) =
        repo.insertTransactionDetail(transactionDetailItem)

    suspend fun getListTransaction(key: String) = repo.getListTransaction(key)

    suspend fun getDetailTransaction(docCode: String, docNumber: String) =
        repo.getDetailTransaction(docCode, docNumber)
}