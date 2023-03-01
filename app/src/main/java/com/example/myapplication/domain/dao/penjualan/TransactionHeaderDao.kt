package com.example.myapplication.domain.dao.penjualan

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.TransactionHeaderItem

@Dao
interface TransactionHeaderDao {

    @Insert
    fun insert(data: TransactionHeaderItem)

    @Query("SELECT * FROM transaction_header_data WHERE _user = :key")
    fun getTransactionHeaderByKey(key: String): TransactionHeaderItem
}