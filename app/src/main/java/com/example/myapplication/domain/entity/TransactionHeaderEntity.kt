package com.example.myapplication.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TransactionHeaderEntity(
    val documentCode: String,
    val documentNumber: String,
    val user: String,
    val total: Int,
    val date: String
) : Parcelable
