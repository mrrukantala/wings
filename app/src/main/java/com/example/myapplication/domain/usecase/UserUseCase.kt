package com.example.myapplication.domain.usecase

import com.example.myapplication.data.model.UserItem
import com.example.myapplication.domain.dao.penjualan.UserDao
import com.example.myapplication.domain.entity.UserD
import com.example.myapplication.domain.repo.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(private val repo: UserRepository) {
    suspend fun insert(data: UserItem) = repo.insertData(data)
    suspend fun getByUser(key: String) = repo.getDataByUser(key)
}