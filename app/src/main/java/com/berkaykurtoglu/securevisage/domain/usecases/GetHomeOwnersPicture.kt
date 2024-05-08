package com.berkaykurtoglu.securevisage.domain.usecases

import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.result.StorageDownloadFileResult
import com.amplifyframework.storage.result.StorageGetUrlResult
import com.berkaykurtoglu.securevisage.domain.repo.HomeOwnerRepository
import java.io.File
import javax.inject.Singleton

@Singleton
class GetHomeOwnersPicture(
    private val repository: HomeOwnerRepository
) {

    operator fun invoke(
        key: String,
        file : File,
        onSuccess: (StorageDownloadFileResult) -> Unit,
        onFailure: (StorageException) -> Unit
    ) = repository.getHomeOwnerPicture(key,file,onSuccess, onFailure)


}