package com.berkaykurtoglu.securevisage.domain.usecases

import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.result.StorageDownloadFileResult
import com.berkaykurtoglu.securevisage.data.repo.EntryScreenRepositoryImpl
import com.berkaykurtoglu.securevisage.domain.repo.EntryScreenRepository
import java.io.File
import javax.inject.Singleton

@Singleton
class GetUserImageUseCase(
    private val entryScreenRepositoryImpl: EntryScreenRepository
) {


    operator fun invoke(
        userName : String,
        file : File,
        onSuccessListener : (StorageDownloadFileResult) -> Unit,
        onFailureListener : (StorageException) -> Unit
    ) = entryScreenRepositoryImpl.getUserImage(
        userName, file, onSuccessListener, onFailureListener
    )


}