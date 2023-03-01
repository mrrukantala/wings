@file:Suppress("UNCHECKED_CAST")

package com.example.bossku.utils.app

import android.content.Context

class SharedPreferences(private val context: Context) {
    companion object {
        private const val PREF = "myApp"
        private const val USER = "user"
    }

    private val sharedPreference = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun saveData(user: String) {
        put(USER, user)
    }

//    fun getToken() = get(PREF_TOKEN, String::class.java)

    fun getUser() = get(USER, String::class.java)

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPreference.getString(key, "")
            Boolean::class.java -> sharedPreference.getBoolean(key, false)
            Float::class.java -> sharedPreference.getFloat(key, -1f)
            Double::class.java -> sharedPreference.getFloat(key, -1f)
            Int::class.java -> sharedPreference.getInt(key, -1)
            Long::class.java -> sharedPreference.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPreference.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    fun clear() {
        sharedPreference.edit().run {
            remove(PREF)
            remove(USER)
        }.apply()
    }
}