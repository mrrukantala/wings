package com.example.bossku.utils

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import com.example.myapplication.R
import com.example.myapplication.databinding.LayoutAlertDialogBinding
import com.example.myapplication.databinding.LayoutDialogLocationBinding
import com.example.myapplication.databinding.LayoutDialogSingleCenterBinding

object DialogUtils {
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private lateinit var binding: LayoutAlertDialogBinding
    private lateinit var bindingCenterButtonDialog: LayoutDialogSingleCenterBinding
    private lateinit var bindingLocationDialog: LayoutDialogLocationBinding

    fun showCustomDialog(
        context: Context,
        title: String,
        message: String = "",
        messageSpan: SpannableStringBuilder? = null,
        positiveAction: Pair<String, (() -> Unit)?>,
        negativeAction: Pair<String, (() -> Unit)?>? = null,
        autoDismiss: Boolean = false,
        buttonAllCaps: Boolean = true,
        margin: Int = 0
    ) {
        binding = LayoutAlertDialogBinding.inflate(LayoutInflater.from(context), null as ViewGroup?, false)
        binding.tvTitle.text = title
        binding.tvMessage.text = message
        binding.btnPositive.let {
            it.text = positiveAction.first
            it.setOnClickListener {
                dialog.dismiss()
                positiveAction.second?.invoke()
            }
            it.isAllCaps = false
        }
        negativeAction?.let { pair ->
            binding.btnNegative.isAllCaps = false
            binding.btnNegative.let {
                it.visibility = View.VISIBLE
                it.text = pair.first
                it.setOnClickListener {
                    dialog.dismiss()
                    pair.second?.invoke()
                }
                it.isAllCaps = false
            }
        }
        builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        builder.setCancelable(autoDismiss)
        dialog = builder.create()
        dialog.show()
    }

    fun showSingleCenterButtonDialog(
        context: Context,
        title: String,
        message: String,
        action: Pair<String, (() -> Unit)?>,
        autoDismiss: Boolean = false,
        buttonAllCaps: Boolean = true
    ): AlertDialog {
        bindingCenterButtonDialog =
            LayoutDialogSingleCenterBinding.inflate(LayoutInflater.from(context))
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_dialog_single_center, null as ViewGroup?, false)
        bindingCenterButtonDialog.tvTitle.text = title
        bindingCenterButtonDialog.tvMessage.text = message
        bindingCenterButtonDialog.btnNeutral.let {
            it.setOnClickListener {
                action.second?.invoke()
                dialog.dismiss()
            }
            it.text = action.first
            it.isAllCaps = buttonAllCaps
        }
        builder = AlertDialog.Builder(context)
        builder.setView(view)
        builder.setCancelable(autoDismiss)
        dialog = builder.create()
        dialog.show()
        return dialog
    }

    fun showSelectLocationDialog(
        context: Context,
        positiveAction: () -> Unit,
        negativeAction: () -> Unit
    ): AlertDialog {
        bindingLocationDialog = LayoutDialogLocationBinding.inflate(LayoutInflater.from(context))
        bindingLocationDialog.btnPositive.let {
            it.setOnClickListener {
                positiveAction.invoke()
                dialog.dismiss()
            }
        }
        negativeAction.let { _ ->
            bindingLocationDialog.btnNegative.let {
                it.setOnClickListener {
                    negativeAction.invoke()
                    dialog.dismiss()
                }
            }
        }
        builder = AlertDialog.Builder(context)
//        builder.setView(bindingLocationDialog)
        builder.setCancelable(true)
        dialog = builder.create()
        dialog.show()
        return dialog
    }

//    fun showCalendarDialog(
//        view: TextInputLayout,
//        fm: FragmentManager
//    ) {
//        CustomDatePicker(0, 0) { year: Int, monthOfYear: Int, dayOfMonth: Int ->
//            view.editText?.setText(
//                String.format(
//                    getStringResource(R.string.format_date2),
//                    String.format(getStringResource(R.string.format_date_2d), (dayOfMonth)),
//                    String.format(getStringResource(R.string.format_date_2d), (monthOfYear + 1)),
//                    year
//                )
//            )
//        }.show(fm, "")
//    }

//    fun showCalendarDialog(
//        maxDate: Long,
//        minDate: Long,
//        view: TextInputLayout,
//        fm: FragmentManager
//    ) {
//        CustomDatePicker(maxDate, minDate) { year: Int, monthOfYear: Int, dayOfMonth: Int ->
//            view.editText?.setText(
//                String.format(
//                    getStringResource(R.string.format_date2),
//                    String.format(getStringResource(R.string.format_date_2d), (dayOfMonth)),
//                    String.format(getStringResource(R.string.format_date_2d), (monthOfYear + 1)),
//                    year
//                )
//            )
//        }.show(fm, "")
//    }

    fun View.changeMargins(margins: Int) {
        val lp = this.layoutParams as ConstraintLayout.LayoutParams
        lp.setMargins(margins)
        this.requestLayout()
    }
}