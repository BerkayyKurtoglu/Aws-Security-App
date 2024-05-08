package com.berkaykurtoglu.securevisage.domain.repo

import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.result.StorageGetUrlResult
import com.amplifyframework.storage.result.StorageListResult

interface HomeOwnerRepository {


    fun getHomeOwnersList(
        onSuccess : (StorageListResult) -> Unit,
        onFailure : (StorageException) -> Unit
    )

    fun getHomeOwnerPicture(
        key : String,
        onSuccess : (StorageGetUrlResult) -> Unit,
        onFailure : (StorageException) -> Unit
    )


}