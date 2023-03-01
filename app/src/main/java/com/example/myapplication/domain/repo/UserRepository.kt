package com.example.myapplication.domain.repo

import com.example.bossku.utils.common.base.Result
import com.example.myapplication.data.model.UserItem
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertData(data: UserItem):
            Flow<Result<UserItem, String>>

    suspend fun getDataByUser(key: String):
            Flow<Result<UserItem, String>>
}