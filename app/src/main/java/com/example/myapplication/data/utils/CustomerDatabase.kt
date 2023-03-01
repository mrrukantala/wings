package com.example.myapplication.data.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.ProductItem
import com.example.myapplication.data.model.TransactionDetailItem
import com.example.myapplication.data.model.TransactionHeaderItem
import com.example.myapplication.data.model.UserItem
import com.example.myapplication.domain.dao.penjualan.UserDao

@Database(
    entities = [UserItem::class, ProductItem::class, TransactionHeaderItem::class, TransactionDetailItem::class],
    version = 1,
    exportSchema = false
)

abstract class PenjualanDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: PenjualanDatabase? = null

        fun getInstance(context: Context): PenjualanDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PenjualanDatabase::class.java,
                        "penjualan_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}