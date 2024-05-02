package com.berkaykurtoglu.securevisage.domain.usecases

data class UseCases(

    val uploadUserImageUseCase: UploadUserImageUseCase,
    val getUserImageUseCase: GetUserImageUseCase,
    val uploadUnknownImageUseCase : UploadUnknownUserImageUseCase

)
