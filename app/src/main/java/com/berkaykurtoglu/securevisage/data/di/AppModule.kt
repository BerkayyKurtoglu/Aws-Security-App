package com.berkaykurtoglu.securevisage.data.di

import android.content.Context
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StorageCategory
import com.berkaykurtoglu.securevisage.data.repo.AlertScreenRepositoryImpl
import com.berkaykurtoglu.securevisage.data.repo.EntryScreenRepositoryImpl
import com.berkaykurtoglu.securevisage.domain.repo.EntryScreenRepository
import com.berkaykurtoglu.securevisage.domain.usecases.GetUserImageUseCase
import com.berkaykurtoglu.securevisage.domain.usecases.UploadUnknownUserImageUseCase
import com.berkaykurtoglu.securevisage.domain.usecases.UploadUserImageUseCase
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideStorage() : StorageCategory = Amplify.Storage


    @Singleton
    @Provides
    fun provideEntryRepo(
        @ApplicationContext context : Context
    ) = EntryScreenRepositoryImpl(context = context)

    @Singleton
    @Provides
    fun provideAlertRepo(
        @ApplicationContext context : Context
    ) = AlertScreenRepositoryImpl(context = context)

    @Singleton
    @Provides
    fun provideUseCases(
        entryScreenRepositoryImpl: EntryScreenRepositoryImpl,
        alertScreenRepositoryImpl: AlertScreenRepositoryImpl
    ) = UseCases(
        uploadUserImageUseCase = UploadUserImageUseCase(entryScreenRepositoryImpl),
        getUserImageUseCase = GetUserImageUseCase(entryScreenRepositoryImpl),
        uploadUnknownImageUseCase = UploadUnknownUserImageUseCase(alertScreenRepositoryImpl)
    )


}