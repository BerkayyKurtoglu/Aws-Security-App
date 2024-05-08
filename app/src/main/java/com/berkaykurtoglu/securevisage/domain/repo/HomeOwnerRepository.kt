package com.berkaykurtoglu.securevisage.domain.repo

import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.result.StorageDownloadFileResult
import com.amplifyframework.storage.result.StorageGetUrlResult
import com.amplifyframework.storage.result.StorageListResult
import java.io.File

interface HomeOwnerRepository {


    fun getHomeOwnersList(
        onSuccess : (StorageListResult) -> Unit,
        onFailure : (StorageException) -> Unit
    )

    fun getHomeOwnerPicture(
        key : String,
        file : File,
        onSuccess : (StorageDownloadFileResult) -> Unit,
        onFailure : (StorageException) -> Unit
    )


}