package com.berkaykurtoglu.securevisage.domain.usecases

import android.net.Uri
import com.amplifyframework.storage.result.StorageUploadInputStreamResult
import com.berkaykurtoglu.securevisage.domain.repo.AlertScreenRepository

class UploadUnknownUserImageUseCase(
    private val repository: AlertScreenRepository
) {

    operator fun invoke(
        uri : Uri,
        onSuccessListener : (StorageUploadInputStreamResult) -> Unit,
        onFailureListener : (Exception) -> Unit
    ) = repository.addUnknownUserImage(uri, onSuccessListener, onFailureListener)


}