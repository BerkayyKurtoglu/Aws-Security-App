package com.berkaykurtoglu.securevisage.domain.usecases

import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.result.StorageDownloadFileResult
import com.berkaykurtoglu.securevisage.data.EntryScreenRepository
import java.io.File
import javax.inject.Singleton

@Singleton
class GetUserImageUseCase(
    private val entryScreenRepository: EntryScreenRepository
) {


    operator fun invoke(
        userName : String,
        file : File,
        onSuccessListener : (StorageDownloadFileResult) -> Unit,
        onFailureListener : (StorageException) -> Unit
    ) = entryScreenRepository.getUserImage(
        userName, file, onSuccessListener, onFailureListener
    )


}