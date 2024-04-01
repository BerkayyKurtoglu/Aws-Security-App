package com.berkaykurtoglu.securevisage.domain.usecases

import android.net.Uri
import com.berkaykurtoglu.securevisage.data.EntryScreenRepository
import javax.inject.Singleton

@Singleton
class UploadUserImageUseCase(
    private val repository: EntryScreenRepository
) {

    operator fun invoke(
        uri : Uri,
        userName : String
    ) = repository.uploadUserImage(uri,userName)

}