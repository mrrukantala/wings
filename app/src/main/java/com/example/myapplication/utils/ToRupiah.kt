package com.example.bossku.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat
import java.util.*

@BindingAdapter("toRupiah")
fun toRupiah(view: TextView, value: String) {
    val formater = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    view.text = formater.format(value.toDoubleOrNull()).toString()
}

fun toRupiah(value: String): String {
    val formater = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    return formater.format(value.toDoubleOrNull())
}