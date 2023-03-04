package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.domain.entity.TransactionDetailEntity

@Entity(tableName = "transaction_detail_data")
data class TransactionDetailItem(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_docment_code")
    val documentCode: String,
    @ColumnInfo(name = "_document_number")
    val documentNumber: String,
    @ColumnInfo(name = "_product_code")
    val productCode: String,
    @ColumnInfo(name = "_price")
    val price: Int,
    @ColumnInfo(name = "_quantity")
    val quantity: Int,
    @ColumnInfo(name = "_unit")
    val unit: String,
    @ColumnInfo(name = "_sub_total")
    val subTotal: Int,
    @ColumnInfo(name = "_currency")
    val currency: String
) {
    fun toTransactionDetailEntity() =
        TransactionDetailEntity(
            documentCode, documentNumber, price, quantity, unit, subTotal, currency
        )
}
