package com.agree.ui.snackbar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.bossku.utils.ui.snackbar.AgrSnackbar
import com.google.android.material.snackbar.Snackbar

fun FragmentActivity.normalSnackBar(message: String) {
    AgrSnackbar.setup(this) {
        title = message
        duration = Snackbar.LENGTH_LONG
        type = AgrSnackbar.ToastType.NORMAL
    }.show()
}

fun FragmentActivity.errorSnackBar(message: String) {
    AgrSnackbar.setup(this) {
        title = message
        duration = Snackbar.LENGTH_LONG
        type = AgrSnackbar.ToastType.ERROR
    }.show()
}

fun FragmentActivity.successSnackBar(message: String) {
    AgrSnackbar.setup(this) {
        title = message
        type = AgrSnackbar.ToastType.SUCCESS
        duration = Snackbar.LENGTH_LONG
    }.show()
}

fun FragmentActivity.warningSnackBar(message: String) {
    AgrSnackbar.setup(this) {
        title = message
        duration = Snackbar.LENGTH_LONG
        type = AgrSnackbar.ToastType.WARNING
    }.show()
}

fun Fragment.successSnackBar(message: String) {
    requireActivity().successSnackBar(message)
}

fun Fragment.warningSnackBar(message: String) {
    requireActivity().warningSnackBar(message)
}

fun Fragment.normalSnackBar(message: String) {
    requireActivity().normalSnackBar(message)
}

fun Fragment.errorSnackBar(message: String) {
    requireActivity().errorSnackBar(message)
}
