package com.example.myapplication.domain.repo

import com.example.bossku.utils.common.base.Result
import com.example.myapplication.data.model.UserItem
import com.example.myapplication.domain.entity.UserD
import kotlinx.coroutines.flow.Flow

interface UserRepository {
        suspend fun insertData(data: UserItem):
            Flow<Result<UserD, UserItem>>

    suspend fun getDataByUser(key: String):
            Flow<Result<UserItem, String>>

    suspend fun loginUser(key: String, password: String):
            Flow<Result<UserD, UserItem>>
}