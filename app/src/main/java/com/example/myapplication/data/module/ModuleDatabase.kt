package com.example.myapplication.data.module

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.repositoryimpl.ProductRepositoryImpl
import com.example.myapplication.data.repositoryimpl.UserRepositoryImpl
import com.example.myapplication.data.utils.PenjualanDatabase
import com.example.myapplication.domain.repo.ProductRepository
import com.example.myapplication.domain.repo.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleDatabase {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun providePenjualanDatabase(@ApplicationContext app: Context) =  PenjualanDatabase.getInstance(app)//Room.databaseBuilder(
//        app,
//        PenjualanDatabase::class.java,
//        "penjualan_database"
//    ).fallbackToDestructiveMigration()
//        .build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideUserDao(db: PenjualanDatabase): UserRepository {
        return UserRepositoryImpl(db.userDao)
    }

    @Singleton
    @Provides
    fun provideProductDao(db: PenjualanDatabase): ProductRepository {
        return ProductRepositoryImpl(db.productDao)
    }
}