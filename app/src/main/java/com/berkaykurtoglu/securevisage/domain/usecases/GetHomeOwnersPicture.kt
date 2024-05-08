package com.berkaykurtoglu.securevisage.domain.usecases

import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.result.StorageGetUrlResult
import com.berkaykurtoglu.securevisage.domain.repo.HomeOwnerRepository
import javax.inject.Singleton

@Singleton
class GetHomeOwnersPicture(
    private val repository: HomeOwnerRepository
) {

    operator fun invoke(
        path: String,
        onSuccess: (StorageGetUrlResult) -> Unit,
        onFailure: (StorageException) -> Unit
    ) = repository.getHomeOwnerPicture(path, onSuccess, onFailure)


}