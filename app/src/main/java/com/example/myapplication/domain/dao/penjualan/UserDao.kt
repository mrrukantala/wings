package com.example.myapplication.domain.dao.penjualan

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.UserItem
import com.example.myapplication.domain.entity.UserD

@Dao
interface UserDao {

    @Insert
    fun insert(userData: UserItem)

    @Query("SELECT * FROM login_data WHERE user = :key")
    fun getUserByUserLogin(key: String): UserItem

    @Query("SELECT * FROM login_data WHERE user = :userName AND _password = :password")
    fun login(userName: String, password: String): UserItem
}