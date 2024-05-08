package com.berkaykurtoglu.securevisage.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream


fun Bitmap.bitmapToUri(
    compressQuality : Int = 100,
    compressFormat : Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    context : Context
) : Uri {

    val bytes = ByteArrayOutputStream()
    this.compress(compressFormat, compressQuality, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, this, "Title", null)
    return Uri.parse(path)

}

fun Uri.uriToBitmap(context : Context) : ImageBitmap {
    return MediaStore.Images.Media.getBitmap(context.contentResolver, this).asImageBitmap()
}


