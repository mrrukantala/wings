package com.example.myapplication.domain.dao.penjualan

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.UserItem

@Dao
interface UserDao {

    @Insert
    fun insert(userData: UserItem)

    @Query("SELECT * FROM login_data WHERE user = :key")
    fun getUserByUserLogin(key: String): UserItem
}