package com.berkaykurtoglu.securevisage.domain.usecases

import android.net.Uri
import com.amplifyframework.storage.result.StorageUploadInputStreamResult
import com.berkaykurtoglu.securevisage.data.EntryScreenRepository
import javax.inject.Singleton

@Singleton
class UploadUserImageUseCase(
    private val repository: EntryScreenRepository
) {

    operator fun invoke(
        uri : Uri,
        userName : String,
        onSuccessListener : (StorageUploadInputStreamResult) -> Unit,
        onFailureListener : (Exception) -> Unit
    ) = repository.uploadUserImage(uri,userName,onSuccessListener,onFailureListener)

}