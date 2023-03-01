package com.example.bossku.utils.ui.image

import android.widget.ImageView
import androidx.core.net.toUri
import coil.load
import coil.request.ImageRequest
import com.example.bossku.utils.PhotoFieldViewWithCropper
import java.io.File

const val PROFILE = ""

class image {

    fun ImageView.url(
        imageUrl: String = "",
        imageFile: File? = null,
        view: ImageView,
        path: String
    ) {
        if (imageUrl.isNotEmpty() || imageFile != null) {
            imageUrl.apply {
                val newImageUrl = ImageRequest.Builder(view.context)
//                    .data("${BASEURLIMAGE}$path/${this.toUri()}")
                    .allowHardware(false)
                    .build()
                view.load("${newImageUrl.data}") {
//                    placeholder(R.drawable.banner)
//                    error(R.drawable.banner)
                }
            }
        }
    }
}

fun uploadListener(saveFile: (File) -> Unit): PhotoFieldViewWithCropper.UploadListener {
    return object : PhotoFieldViewWithCropper.UploadListener {
        override fun onUploadPicture() {
        }

        override fun onFinish(file: File) {
            saveFile(file)
        }
    }
}