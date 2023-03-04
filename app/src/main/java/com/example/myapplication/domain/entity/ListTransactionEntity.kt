package com.example.myapplication.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ListTransactionEntity(
    val documentCode: String,
    val user: String,
    val total: String,
    val date: String,
    val price: String,
    val quantity: String
) : Parcelable


@Keep
@Parcelize
data class DetailTransactionEntity(
    val documentCode: String,
    val docuNumber: Number,
    val unit: String,
    val subTotal: String,
    val currency: String,
    val productCode: String,
    val discount: String
) : Parcelable