package com.example.bossku.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputLayout

object SpanUtil {
    @SuppressLint("ResourceAsColor")
    fun createMarkRequiredSpan(view: View, text: String) {
        val markColor = R.color.red_error
        val markNotation = " *"

        if (view is TextView) {
            view.text = buildSpannedString {
                append(text)
                color(markColor) {
                    append(markNotation)
                }
            }
        } else if (view is TextInputLayout) {
            view.hint = buildSpannedString {
                append(text)
                color(markColor) {
                    append(markNotation)
                }
            }
        }
    }

    fun createOptionalSpan(view: View, text: String, isRequired: Boolean? = true) {
        val optionalLabel = "Opsional"
        if (isRequired == false) {
            if (view is TextView) {
                view.text = buildSpannedString {
                    append(text)
                    append(" ")
                    append(optionalLabel)
                }
            } else if (view is TextInputLayout) {
                view.hint = buildSpannedString {
                    append(text)
                    append(" ")
                    append(optionalLabel)
                }
            }
        } else {
            createMarkRequiredSpan(view, text)
        }
    }
}