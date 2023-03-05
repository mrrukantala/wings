package com.example.myapplication.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ListTransactionEntity(
    @ColumnInfo(name = "_document_code")
    val documentCode: String,
    @ColumnInfo(name = "_document_number")
    val docuNumber: String,
    @ColumnInfo(name = "_product_name")
    val namaProduct: String,
    @ColumnInfo(name = "_user")
    val user: String,
    @ColumnInfo(name = "_total")
    val total: String,
    @ColumnInfo(name = "_date")
    val date: String,
    @ColumnInfo(name = "_price")
    val price: String,
    @ColumnInfo(name = "_quantity")
    val quantity: String
) : Parcelable


@Keep
@Parcelize
data class DetailTransactionEntity(
    @ColumnInfo(name = "_docment_code")
    val documentCode: String,
    @ColumnInfo(name = "_document_number")
    val docuNumber: String,
    @ColumnInfo(name = "_unit")
    val unit: String,
    @ColumnInfo(name = "_sub_total")
    val subTotal: String,
    @ColumnInfo(name = "_currency")
    val currency: String,
    @ColumnInfo(name = "productCode")
    val productCode: String,
    @ColumnInfo(name = "_discount")
    val discount: String
) : Parcelable