package com.example.bossku.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.TypedArray
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import coil.load
import coil.request.ImageRequest
import com.example.bossku.utils.app.UploadCallbackWithCrop
import com.example.myapplication.R
import com.example.myapplication.databinding.LayoutPhotoAvatarBinding
import com.example.myapplication.databinding.LayoutPhotoFieldCollateralBinding
import java.io.File
import java.io.IOException

class PhotoFieldViewWithCropper(
    context: Context,
    attrs: AttributeSet
) :
    ConstraintLayout(context, attrs),
    UploadCallbackWithCrop {
    private var callback: UploadCallbackWithCrop
    private var photoBottomSheet: PhotoBottomSheetWithCropper
    var image: File? = null
    var imageBase64: String? = null
    var title: String = ""
    private var photoHolder: ImageView
    private val type: String
    private var styledAttributes: TypedArray
    private var optional = false
    private var photoFieldListener: UploadListener? = null
    var isPreviewEnabled = false
    private lateinit var binding: LayoutPhotoFieldCollateralBinding
    private lateinit var bindingAvatar: LayoutPhotoAvatarBinding


    init {
        attrs.let {
            styledAttributes =
                context.obtainStyledAttributes(it, R.styleable.PhotoFieldView, 0, 0)
            type = styledAttributes.getString(R.styleable.PhotoFieldView_type_image) ?: "0"
            optional = styledAttributes.getBoolean(R.styleable.PhotoFieldView_optional, false)
        }
        if (type == "0") {
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layout_photo_field_collateral,
                null,
                false
            )
            this.addView(binding.root)
            binding.tvUploadPhoto.setOnClickListener {
                clickUpload()
            }
            val text = styledAttributes.getString(R.styleable.PhotoFieldView_text_image)
            setText(text)
            photoHolder = binding.imgPhoto
        } else {
            bindingAvatar = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layout_photo_avatar,
                null,
                false
            )
            this.addView(bindingAvatar.root)

            val enabled = styledAttributes.getBoolean(R.styleable.PhotoFieldView_enabled, true)
            if (!enabled) {
                bindingAvatar.ivTakePhotoAvatar.visibility = View.GONE
            } else {
                bindingAvatar.containerPhotoAvatar.setOnClickListener {
                    clickUpload()
                }
            }
            photoHolder = bindingAvatar.ivAvatar
        }
        callback = this
//        context as FragmentActivity
        photoBottomSheet = PhotoBottomSheetWithCropper(callback, true)
    }

    private fun clickUpload() {
        if (photoFieldListener != null) {
            photoFieldListener!!.onUploadPicture()
        }

        try {
            photoBottomSheet.show(
                (((context as ContextWrapper).baseContext) as FragmentActivity).supportFragmentManager,
                "photoBottomSheet"
            )
        } catch (e: Exception) {
            Toast.makeText(context, "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun isPickerEnabled(value: Boolean = true) {
        if (!value) {
            binding.tvUploadPhoto.setOnClickListener {
                // Do Nothing
            }
            binding.tvUploadPhoto.setTextColor(ContextCompat.getColor(context, R.color.grey_cf))
        } else {
            binding.tvUploadPhoto.setTextColor(ContextCompat.getColor(context, R.color.red50))
            binding.tvUploadPhoto.setOnClickListener {
                clickUpload()
            }
        }
    }

    fun setText(text: String?, required: Boolean = false) {
        title = text.toString()
        if (text != null) {
            if (!optional || required) {
                SpanUtil.createMarkRequiredSpan(binding.tvPhotoTitle, title)
            } else {
                binding.tvPhotoTitle.text = title
            }
        }
    }

    fun setRequired(value: Boolean = true) {
        if (value) {
            SpanUtil.createMarkRequiredSpan(binding.tvPhotoTitle, title)
        } else {
            binding.tvPhotoTitle.text = title
        }
    }

    fun setTextError(text: String = "") {
        binding.viewDivider.visibility = View.VISIBLE
        binding.tvHelper.visibility = View.VISIBLE
//        binding.tvHelper.setTextColor(ContextCompat.getColor(context, R.color.red))
        if (text != "") {
            binding.tvHelper.text = text
        }
    }

    private fun clearTextError() {
        binding.viewDivider.visibility = View.GONE
        binding.tvHelper.visibility = View.GONE
        binding.tvHelper.setTextColor(ContextCompat.getColor(context, R.color.black1))
    }

    fun url(imageUrl: String = "", imageFile: File? = null, path: String) {
        if (imageUrl.isNotEmpty() || imageFile != null) {
            imageUrl.apply {
                val imageUrl = ImageRequest.Builder(photoHolder.context)
//                    .data("${BASEURLIMAGE}$path/${this.toUri()}")
                    .allowHardware(false)
                    .build()
                photoHolder.load("${imageUrl.data}") {
//                    placeholder(R.drawable.banner)
//                    error(R.drawable.banner)
                }
            }
        }
    }

    //
//    fun containsImage(setError: Boolean): Boolean {
//        if (imageBase64.isNull()) {
//            if (setError)
//                setTextError()
//        }
//        return imageBase64.isNotNull()
//    }
//
    interface UploadListener {
        fun onUploadPicture()

        fun onFinish(file: File)
    }

    fun addUploadListner(listener: UploadListener) {
        if (photoFieldListener != null) {
            throw Exception("listener has assigned")
        }

        photoFieldListener = listener
    }

    override fun uploadWithCropper(uri: Uri) {
        val file = File(uri.path.toString())
        try {
            if (type == "0") {
                clearTextError()
                binding.tvUploadPhoto.text =
                    if (file.name.isEmpty()) "Unggah Foto" else "Unggah Lagi"
            }

            image = file
            val bm = BitmapFactory.decodeFile(image!!.path)
            val adjustedBitmap = adjustImageRotation(bm, file)
            imageBase64 = getBase64(adjustedBitmap)
            photoHolder.setImageBitmap(adjustedBitmap)
            if (photoFieldListener != null) {
                photoFieldListener!!.onFinish(file)
            }
            photoHolder.setImageURI(uri)
            photoBottomSheet.dismiss()
        } catch (e: IOException) {
            if (type == "0") {
                setTextError()
            }
        }
    }
}