package com.example.myapplication.data.repositoryimpl

import com.example.bossku.utils.common.base.Result
import com.example.myapplication.data.model.UserItem
import com.example.myapplication.domain.dao.penjualan.UserDao
import com.example.myapplication.domain.repo.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dao: UserDao) :
    UserRepository {

    override suspend fun insertData(data: UserItem): Flow<Result<UserItem, String>> {
        return flow {
            dao.insert(data)
            emit(Result.Success(data))
        }
    }

    override suspend fun getDataByUser(key: String): Flow<Result<UserItem, String>> {
        return flow {
            val data = dao.getUserByUserLogin(key)
            if (data.user.equals(key, true)) {
                emit(Result.Error(data.user))
            } else {
                emit(Result.Success(data))
            }
        }
    }

//    override suspend fun getDataByUser(key: String): Flow<Result<UserItem, String>> {
//        return flow {
//            val data = dao.getUserByUserLogin(key)
//            emit(Result.Success(data))
//        }
//    }
}