package com.berkaykurtoglu.securevisage.data.repo

import android.content.Context
import android.net.Uri
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StorageCategory
import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.result.StorageDownloadFileResult
import com.amplifyframework.storage.result.StorageUploadInputStreamResult
import com.berkaykurtoglu.securevisage.domain.repo.EntryScreenRepository
import com.berkaykurtoglu.securevisage.utils.Resource
import java.io.File
import javax.inject.Singleton

@Singleton
class EntryScreenRepositoryImpl(
    private val storage : StorageCategory = Amplify.Storage,
    val context: Context
) : EntryScreenRepository {


    override fun uploadUserImage(
        uri : Uri,
        userName : String,
        onSuccessListener : (StorageUploadInputStreamResult) -> Unit,
        onFailureListener : (Exception) -> Unit
    ) {
        val stream = context.contentResolver.openInputStream(uri)
        stream?.let {
            storage.uploadInputStream(
                "homeowner/${userName}.jpeg",
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

    override fun getUserImage(
        userName : String,
        file : File,
        onSuccessListener : (StorageDownloadFileResult) -> Unit,
        onFailureListener : (StorageException) -> Unit
    ) {
            storage.downloadFile(
                "homeowner/${userName}.jpeg",
                file,
                {
                    onSuccessListener(it)
                },
                {
                    onFailureListener(it)
                }
            )
            Resource.Success(true)
     }




}