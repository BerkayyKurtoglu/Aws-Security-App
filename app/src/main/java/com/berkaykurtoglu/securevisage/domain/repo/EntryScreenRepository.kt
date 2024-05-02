package com.berkaykurtoglu.securevisage.domain.repo

import android.net.Uri
import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.result.StorageDownloadFileResult
import com.amplifyframework.storage.result.StorageUploadInputStreamResult
import java.io.File
import javax.inject.Singleton

@Singleton
interface EntryScreenRepository {

    fun uploadUserImage(
        uri : Uri,
        userName : String,
        onSuccessListener : (StorageUploadInputStreamResult) -> Unit,
        onFailureListener : (Exception) -> Unit
    )

    fun getUserImage(
        userName : String,
        file : File,
        onSuccessListener : (StorageDownloadFileResult) -> Unit,
        onFailureListener : (StorageException) -> Unit
    )



}