package com.example.myapplication.domain.dao.penjualan

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.TransactionDetailItem

@Dao
interface TransactionDetailDao {
    @Insert
    fun insert(data: TransactionDetailItem)

    @Query("SELECT * FROM transaction_detail_data WHERE _document_code= :key")
    fun getTransactionDetailByKey(key: String): TransactionDetailItem
}