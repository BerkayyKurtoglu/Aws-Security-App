package com.berkaykurtoglu.securevisage.data.repo

import android.content.Context
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.StoragePath
import com.amplifyframework.storage.options.StoragePagedListOptions
import com.amplifyframework.storage.result.StorageGetUrlResult
import com.amplifyframework.storage.result.StorageListResult
import com.berkaykurtoglu.securevisage.domain.repo.HomeOwnerRepository
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
            StoragePath.fromString("homeowner/"),
            options,
            onSuccess,
            onFailure
        )
    }

    override fun getHomeOwnerPicture(
        path: String,
        onSuccess: (StorageGetUrlResult) -> Unit,
        onFailure: (StorageException) -> Unit
    ) {
        Amplify.Storage.getUrl(
            StoragePath.fromString("homeowner/${path}.jpeg"),
            onSuccess,
            onFailure
        )
    }
}