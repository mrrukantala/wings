package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.domain.entity.UserD

@Entity(tableName = "login_data")
data class UserItem(
    @PrimaryKey(autoGenerate = false)
    val user: String,

    @ColumnInfo(name = "_password")
    val password: String
) {
    fun toUserEntity() =
        UserD(user, password)
}
