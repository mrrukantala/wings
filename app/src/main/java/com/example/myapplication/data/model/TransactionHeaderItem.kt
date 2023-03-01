package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.domain.entity.TransactionHeaderEntity

@Entity(tableName = "transaction_header_data")
data class TransactionHeaderItem(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_document_code")
    val documentCode: String,
    @ColumnInfo(name = "_document_number")
    val documentNumber: String,
    @ColumnInfo(name = "_user")
    val user: String,
    @ColumnInfo(name = "_total")
    val total: Int,
    @ColumnInfo(name = "_date")
    val date: String
) {
    fun toTransactionHeaderEntity() =
        TransactionHeaderEntity(
            documentCode,
            documentNumber, user, total, date
        )
}
