package com.example.myapplication.domain.dao.penjualan

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.ProductItem

@Dao
interface ProductDao {
    @Insert
    fun insert(productData: ProductItem)

    @Query("SELECT * FROM product_data WHERE _user != :key")
    fun getAllProductData(key: String): List<ProductItem>

    @Query("SELECT * FROM product_data WHERE _user =:key")
    fun getListProductByUser(key: String): List<ProductItem>

    @Query("SELECT * FROM product_data WHERE productCode = :key")
    fun getProductByCode(key: String): ProductItem
}