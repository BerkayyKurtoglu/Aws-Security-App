package com.berkaykurtoglu.securevisage.domain.repo

import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.result.StorageListResult

interface HomeOwnerRepository {


    fun getUserDetail(
        path: String,
        onSuccess : (StorageListResult) -> Unit,
        onFailure : (StorageException) -> Unit
    )


}