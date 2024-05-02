package com.berkaykurtoglu.securevisage.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
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


