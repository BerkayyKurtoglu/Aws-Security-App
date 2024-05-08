package com.berkaykurtoglu.securevisage.data.repo

import android.content.Context
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.options.StoragePagedListOptions
import com.amplifyframework.storage.result.StorageListResult
import com.berkaykurtoglu.securevisage.domain.repo.HomeOwnerRepository
import javax.inject.Singleton

@Singleton
class HomeOwnerRepositoryImpl(
    private val context : Context
) : HomeOwnerRepository {

    private val options = StoragePagedListOptions.builder()
        .setPageSize(1000)
        .build()

    override fun getHomeOwnersList(
        onSuccess : (StorageListResult) -> Unit,
        onFailure : (StorageException) -> Unit
    ) {
        Amplify.Storage.list(
            "homeowner/",
            options,
            onSuccess,
            onFailure
        )
    }
}