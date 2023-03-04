package com.example.myapplication.data.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.ProductItem
import com.example.myapplication.data.model.TransactionDetailItem
import com.example.myapplication.data.model.TransactionHeaderItem
import com.example.myapplication.data.model.UserItem
import com.example.myapplication.domain.dao.penjualan.ProductDao
import com.example.myapplication.domain.dao.penjualan.TransactionDao
import com.example.myapplication.domain.dao.penjualan.UserDao

@Database(
    entities = [UserItem::class, ProductItem::class, TransactionHeaderItem::class, TransactionDetailItem::class],
    version = 1,
    exportSchema = false
)

abstract class PenjualanDatabase : RoomDatabase() {

    abstract val userDao : UserDao
    abstract val productDao : ProductDao
    abstract val transactionDao: TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: PenjualanDatabase? = null

        fun getInstance(context: Context): PenjualanDatabase {
            return INSTANCE ?: synchronized(this) {
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    PenjualanDatabase::class.java,
                    "penjualan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}