package com.example.bossku.utils

import android.util.Log
import com.example.bossku.utils.enums.ConverterDate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val TODAY = 24 * HOUR_MILLIS
private const val YESTERDAY = 2 * TODAY

internal fun String.convertTimeToListNotif(desireFormat: ConverterDate?): String {
    return try {
        val dateFormat = SimpleDateFormat(ConverterDate.UTC.formatter, Locale("id", "ID"))

        val date = dateFormat.parse(this) ?: Date()
        val hour = 3600 * 1000
        val newDate = Date(date.time + 7 * hour)
        dateFormat.applyPattern(desireFormat?.formatter)
        val deffer = Calendar.getInstance().time.time - newDate.time
        var returnValue: String? = null

        if (deffer > YESTERDAY) returnValue = dateFormat.format(newDate).toString()
        if (deffer in (TODAY + 1) until YESTERDAY) returnValue = "Kemarin"
        if (deffer < TODAY) returnValue = "Hari Ini"
        returnValue.orEmpty()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String.convertUTC2TimeTo2(desireFormat: ConverterDate): String {
    val dateFormat = SimpleDateFormat(ConverterDate.UTC.formatter, Locale("id", "ID"))
    return try {
        val date = dateFormat.parse(this) ?: Date()
        val hour = 3600 * 1000
        val newDate = Date(date.time + 7 * hour)
        dateFormat.applyPattern(desireFormat.formatter)
        dateFormat.format(newDate).toString()
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun String.convertSIMPLE_DATETimeTo2(desireFormat: ConverterDate): String {
    val dateFormat = SimpleDateFormat(ConverterDate.SIMPLE_DATE2. formatter, Locale("id", "ID"))
    return try {
        val date = dateFormat.parse(this) ?: Date()
        val hour = 3600 * 1000
        val newDate = Date(date.time + (7 * hour))
        dateFormat.applyPattern(desireFormat.formatter)
        dateFormat.format(newDate).toString()
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}