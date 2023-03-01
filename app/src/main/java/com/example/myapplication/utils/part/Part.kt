package com.example.bossku.utils.part

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun createPart(value: String): RequestBody = value.toRequestBody()

fun createImagePart(file: File?, name: String): MultipartBody.Part {
    val body = file?.asRequestBody("image/*".toMediaTypeOrNull())
    return if (file == null) MultipartBody.Part.createFormData(
        name,
        file?.name ?: ""
    ) else MultipartBody.Part.createFormData(name, file.name, body!!)
}

fun createImagePartName(nameFile: String?, name: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        name,
        nameFile ?: ""
    )
}