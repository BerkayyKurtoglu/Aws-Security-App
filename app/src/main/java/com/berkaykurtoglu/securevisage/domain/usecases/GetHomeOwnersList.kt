package com.berkaykurtoglu.securevisage.domain.usecases

import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.result.StorageListResult
import com.berkaykurtoglu.securevisage.domain.repo.HomeOwnerRepository
import javax.inject.Singleton

@Singleton
class GetHomeOwnersList(
    private val homeOwnersRepository: HomeOwnerRepository
) {

    operator fun invoke(
        onSuccess : (StorageListResult) -> Unit,
        onFailure : (StorageException) -> Unit
    ){
       homeOwnersRepository.getHomeOwnersList(onSuccess, onFailure)
    }


}