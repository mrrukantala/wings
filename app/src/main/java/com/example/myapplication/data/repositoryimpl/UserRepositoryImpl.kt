package com.example.myapplication.data.repositoryimpl

import android.util.Log
import com.example.bossku.utils.common.base.Result
import com.example.myapplication.data.model.UserItem
import com.example.myapplication.domain.dao.penjualan.UserDao
import com.example.myapplication.domain.entity.UserD
import com.example.myapplication.domain.repo.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dao: UserDao) :
    UserRepository {
    override suspend fun insertData(data: UserItem): Flow<Result<UserD, UserItem>> {
        return flow {
            delay(1000)
            dao.insert(data)
            emit(Result.Success(data.toUserEntity()))
        }
    }

    override suspend fun getDataByUser(key: String): Flow<Result<UserItem, String>> {
        return flow {
            val data = dao.getUserByUserLogin(key)
            emit(Result.Success(data))
        }
    }

    override suspend fun loginUser(key: String, password: String): Flow<Result<UserD, UserItem>> {
        return flow {
            val result = dao.login(key, password)
            Log.v("DATA", result.toUserEntity().toString() ?: result.toString())
            if (result.user.isNotEmpty()) {
                Log.v("DATA", result.toUserEntity().toString())
                emit(Result.Success(result.toUserEntity()))
            } else {
                Log.v("DATA", result.toUserEntity().toString())
                emit(Result.Error(result))
            }
        }
    }
}