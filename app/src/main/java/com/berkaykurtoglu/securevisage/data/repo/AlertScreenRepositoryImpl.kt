package com.berkaykurtoglu.securevisage.data.repo

import android.content.Context
import android.net.Uri
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StorageCategory
import com.amplifyframework.storage.result.StorageUploadInputStreamResult
import com.berkaykurtoglu.securevisage.domain.repo.AlertScreenRepository
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Singleton

@Singleton
class AlertScreenRepositoryImpl(
    val storage : StorageCategory = Amplify.Storage,
    val context: Context
) : AlertScreenRepository {

    override fun addUnknownUserImage(
        uri: Uri,
        onSuccessListener: (StorageUploadInputStreamResult) -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {

        val stream = context.contentResolver.openInputStream(uri)
        val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date())
        stream?.let {
            storage.uploadInputStream(
                "unknownFaces/${date}.jpeg",
                stream,
                {
                    onSuccessListener(it)
                },
                {
                    onFailureListener(it)
                }
            )
        } ?: {
            onFailureListener(Exception("Error occurred"))
        }

    }

}