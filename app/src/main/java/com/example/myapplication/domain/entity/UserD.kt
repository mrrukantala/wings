package com.example.myapplication.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserD(
    val user: String,
    val password: String
): Parcelable
