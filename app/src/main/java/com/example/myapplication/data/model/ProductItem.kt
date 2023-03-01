package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.domain.entity.ProductEntity

@Entity(tableName = "product_data")
data class ProductItem(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_product_code")
    val productCode: String,

    @ColumnInfo(name = "_product_name")
    val productName: String,

    @ColumnInfo(name = "_price")
    val price: Int,

    @ColumnInfo(name = "_currency")
    val currency: String,

    @ColumnInfo(name = "_discount")
    val discount: Int,

    @ColumnInfo(name = "_dimension")
    val dimension: String,

    @ColumnInfo(name = "_unit")
    val unit: String
) {
    fun toProductEntity() =
        ProductEntity(productCode, productName, price, currency, discount, dimension, unit)
}
