package com.berkaykurtoglu.securevisage.domain.repo

import android.net.Uri
import com.amplifyframework.storage.result.StorageUploadInputStreamResult

interface AlertScreenRepository {


    fun addUnknownUserImage(
        uri : Uri,
        onSuccessListener : (StorageUploadInputStreamResult) -> Unit,
        onFailureListener : (Exception) -> Unit
    )


}