package com.example.bossku.utils.app

import android.net.Uri

interface UploadCallbackWithCrop {
    fun uploadWithCropper(uri: Uri)
}

interface PreviewCroppedPhotoCallback {
    fun onChooseAgain(int: Int)
}