package com.example.bossku.data.auth

import com.google.gson.annotations.SerializedName

data class ResponseListWrapper<T>(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<T>? = null
)

data class ResponseObjectWrapper<T>(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T? = null
)

