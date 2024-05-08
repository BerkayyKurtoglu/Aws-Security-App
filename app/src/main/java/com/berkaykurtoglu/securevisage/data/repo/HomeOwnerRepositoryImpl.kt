package com.berkaykurtoglu.securevisage.data.repo

import android.content.Context
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.StoragePath
import com.amplifyframework.storage.options.StoragePagedListOptions
import com.amplifyframework.storage.result.StorageDownloadFileResult
import com.amplifyframework.storage.result.StorageGetUrlResult
import com.amplifyframework.storage.result.StorageListResult
import com.berkaykurtoglu.securevisage.domain.repo.HomeOwnerRepository
import java.io.File
import javax.inject.Singleton

@Singleton
class HomeOwnerRepositoryImpl(
    private val context : Context
) : HomeOwnerRepository {

    private val options = StoragePagedListOptions.builder()
        .setPageSize(50)
        .build()

    override fun getHomeOwnersList(
        onSuccess : (StorageListResult) -> Unit,
        onFailure : (StorageException) -> Unit
    ) {
        Amplify.Storage.list(
            StoragePath.fromString("public/homeowner/"),
            options,
            onSuccess,
            onFailure
        )
    }

    override fun getHomeOwnerPicture(
        key: String,
        file : File,
        onSuccess: (StorageDownloadFileResult) -> Unit,
        onFailure: (StorageException) -> Unit
    ) {
        Amplify.Storage.downloadFile(
            StoragePath.fromString("public/homeowner/$key.jpeg"),
            file,
            onSuccess,
            onFailure
        )
    }
}