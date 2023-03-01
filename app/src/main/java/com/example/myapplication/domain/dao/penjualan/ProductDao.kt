package com.example.myapplication.domain.dao.penjualan

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.ProductItem

@Dao
interface ProductDao {
    @Insert
    fun insert(productData: ProductItem)

    @Query("SELECT * FROM product_data")
    fun getAllProductData(): LiveData<List<ProductItem>>

    @Query("SELECT * FROM product_data WHERE _product_code = :key")
    fun getProductByCode(key: String): ProductItem
}