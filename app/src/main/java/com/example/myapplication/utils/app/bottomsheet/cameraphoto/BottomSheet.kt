package com.example.bossku.utils.app.bottomsheet.cameraphoto

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import com.example.bossku.utils.PhotoResultPreview
import com.example.bossku.utils.app.PreviewCroppedPhotoCallback
import com.example.bossku.utils.app.UploadCallbackWithCrop
import com.example.myapplication.R
import com.example.myapplication.databinding.LayoutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class BottomSheet(
    private val callback: UploadCallbackWithCrop,
    val enablePreview: Boolean = false
) : BottomSheetDialogFragment() {

    private lateinit var binding: LayoutBottomSheetBinding

    private lateinit var currentPhotoPath: String
    private val PERMISSION_CODE_CAMERA = 1000
    private val PERMISSION_CODE_STORAGE = 1001
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_GALLERY = 2

    private var sheet = this

    private lateinit var photoResultPreview: PhotoResultPreview

    private var photoPreviewCallback = object : PreviewCroppedPhotoCallback {
        override fun onChooseAgain(int: Int) {
            try {
                sheet.show((context as FragmentActivity).supportFragmentManager, "PhotoBottomSheet")
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "${getString(R.string.label_error)} ${e.message}",
                    Toast.LENGTH_LONG
                )
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutBottomSheetBinding.inflate(inflater, container, false)
        binding.containerCamera.setOnClickListener {
            clickCamera()
        }

        binding.containerGallery.setOnClickListener {
            clickGallery()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.containerCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) ==
                PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_DENIED
            ) {
                val permission = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                requestPermissions(permission, PERMISSION_CODE_CAMERA)
            } else {
                clickCamera()
            }
        }

        binding.containerGallery.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_DENIED
            ) {
                val permission = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                requestPermissions(permission, PERMISSION_CODE_STORAGE)
            } else {
                clickGallery()
            }
        }
        binding.imgBtnBatal.setOnClickListener {
            dismiss()
        }
    }

    fun clickGallery() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        try {
            startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY)
        } catch (e: ActivityNotFoundException) {
            Log.e("ERROR", "No Activity Found")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    for (result in grantResults) {
                        if (result == PackageManager.PERMISSION_DENIED) {
                            return
                        }
                    }
                    clickCamera()
                }
            }
            PERMISSION_CODE_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    for (result in grantResults) {
                        if (result == PackageManager.PERMISSION_DENIED) {
                            return
                        }
                    }
                    clickGallery()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            startCrop(File(currentPhotoPath).toUri())
        }
        if (data != null) {
            if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK) {
                data.data?.let {
                    startCrop(it)
                } ?: Log.e("", "Error when pick image from gallery")
            } else if (requestCode == UCrop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
                val imageResCrop = UCrop.getOutput(data)
                if (imageResCrop != null) {

                    // if preview enabled, show preview fragment
                    if (enablePreview) {
                        photoResultPreview =
                            PhotoResultPreview(callback, photoPreviewCallback, imageResCrop)
                        photoResultPreview.show(
                            (context as FragmentActivity).supportFragmentManager,
                            "photoPreview"
                        )
                    } else {
                        // else, send cropped image back to CropperCustomView
                        callback.uploadWithCropper(imageResCrop)
                    }
                }
            }
        }
    }


    private fun clickCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.bossku.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun startCrop(uri: Uri) {
        val destName = "${System.currentTimeMillis()}.jpg"
        val uCrop = UCrop.of(uri, Uri.fromFile(File(context?.cacheDir, destName)))

        UCrop.of(uri, Uri.fromFile(File(context?.cacheDir, destName)))
            .withAspectRatio(1.toFloat(), 1.toFloat())
            .withOptions(getOptions())
            .start(requireContext(), this)
    }

    private fun getOptions(): UCrop.Options {
        val options = UCrop.Options()
        options.setCompressionQuality(70)
        options.setFreeStyleCropEnabled(true)
        options.setToolbarWidgetColor(ContextCompat.getColor(context as Activity, R.color.white))
        options.setToolbarColor(ContextCompat.getColor(context as Activity, R.color.red50))
        options.setToolbarTitle("Edit Photo")
        return options
    }


}