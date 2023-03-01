package com.example.bossku.utils.common.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

sealed class Result<out T : Any, out U : Any> {
    data class Success<T : Any>(val data: T) : Result<T, Nothing>()
    data class Error<U : Any>(val response: U) : Result<Nothing, U>()
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}