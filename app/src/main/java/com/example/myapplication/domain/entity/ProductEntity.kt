package com.example.myapplication.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ProductEntity(
    val productCode: String,
    val productName: String,
    val user: String,
    val price: Int,
    val currency: String,
    val discount: Int,
    val dimension: String,
    val unit: String
) : Parcelable
