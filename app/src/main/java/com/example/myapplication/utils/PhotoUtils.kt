package com.example.bossku.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.util.Base64
import android.view.View
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun adjustImageRotation(bitmap: Bitmap, photoPath: File): Bitmap {
    val ei = ExifInterface(photoPath)
    val orientation: Int = ei.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_UNDEFINED
    )

    lateinit var rotatedBitmap: Bitmap
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap, 90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap, 180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap, 270f)
        ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
        else -> rotatedBitmap = bitmap
    }
    return rotatedBitmap
}

fun rotateImage(source: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height,
        matrix, true
    )
}

fun getBase64(bm: Bitmap): String {
    val resizedBm = Bitmap.createScaledBitmap(bm, 600, 600, false)
    val bOut = ByteArrayOutputStream()
    resizedBm.compress(Bitmap.CompressFormat.PNG, 100, bOut)
    val strImage = Base64.encodeToString(bOut.toByteArray(), Base64.NO_WRAP)
    val imagebyeMap = bOut.toByteArray()
    if (imagebyeMap.size > 2000 * 1024) {
        throw IOException("File lebih dari batas maksimal yaitu 2 mb")
    }
    return "data:image/png;base64,$strImage"
}

fun bitmapToFile(
    context: Context,
    bitmap: Bitmap,
    filename: String
): File? {
    var file: File? = null
    return try {
        file = File(context.filesDir, filename)
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
        val bitmapdata = bos.toByteArray()

        val fos = FileOutputStream(file)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        file // it will return null
    }
}

fun getBitmapFromView(view: View): Bitmap {
    // Define a bitmap with the same size as the view
    val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    // Bind a canvas to it
    val canvas = Canvas(returnedBitmap)
    // Get the view's background
    val bgDrawable = view.background
    if (bgDrawable != null) {
        // has background drawable, then draw it on the canvas
        bgDrawable.draw(canvas)
    } else {
        // does not have background drawable, then draw white background on the canvas
        canvas.drawColor(Color.WHITE)
    }
    // draw the view on the canvas
    view.draw(canvas)
    // return the bitmap
    return returnedBitmap
}

//fun scanGallery(path: String, context: Context) {
//    try {
//        MediaScannerConnection.scanFile(
//            context, arrayOf(path), null
//        ) { _, _ -> displayToast(context, "Download Complete!", false) }
//    } catch (e: Exception) {
//        e.printStackTrace()
//        Timber.tag("errorInfo").i("There was an issue scanning gallery.")
//    }
//}