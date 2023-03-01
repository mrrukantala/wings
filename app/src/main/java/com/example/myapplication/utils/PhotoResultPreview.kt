package com.example.bossku.utils

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.bossku.utils.app.PreviewCroppedPhotoCallback
import com.example.bossku.utils.app.UploadCallbackWithCrop
import com.example.myapplication.R
import com.example.myapplication.databinding.LayoutPreviewPhotoBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PhotoResultPreview(
    private val callback: UploadCallbackWithCrop,
    private val callbackPrev: PreviewCroppedPhotoCallback,
    private val imageFile: Uri? = null
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: LayoutPreviewPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutPreviewPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.currentImage.setImageURI(imageFile)

        binding.tvPhotoSuccessDesc.text =
            getString(R.string.label_photo_taken_time, "")//getCurrentHourString())

        binding.btnCancel.setOnClickListener {
            this.dismiss()
        }

        binding.btnChooseAgain.setOnClickListener {
            this.dismiss()
        }

        binding.btnSavePhotoPreview.setOnClickListener {
            if (imageFile != null) {
                callback.uploadWithCropper(imageFile)
            }
            this.dismiss()
        }
    }
}