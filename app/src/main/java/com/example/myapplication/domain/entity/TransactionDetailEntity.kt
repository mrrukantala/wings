package com.example.myapplication.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TransactionDetailEntity(
    val documentCode: String,
    val documentNumber: String,
    val price: Int,
    val quantity: Int,
    val unit: String,
    val subTotal: Int,
    val currency: String
) : Parcelable