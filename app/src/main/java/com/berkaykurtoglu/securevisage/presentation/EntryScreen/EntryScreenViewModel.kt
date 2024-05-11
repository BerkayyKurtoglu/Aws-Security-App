package com.berkaykurtoglu.securevisage.presentation.EntryScreen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amplifyframework.ui.authenticator.SignedInState
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EntryScreenViewModel @Inject constructor (
    private val useCases : UseCases,
    @ApplicationContext var context : Context
) :ViewModel() {

    private val _state = mutableStateOf(EntryScreenState())
    val state : State<EntryScreenState> = _state


    fun onEvent(event : EntryScreenEvent){
        when(event){
            is EntryScreenEvent.OnUploadUserImageEvent -> {
                uploadUserImage(event.uri,event.userName)
            }
            is EntryScreenEvent.OnGetUserImageEvent -> {
                getUserImage(event.userState)
            }
        }
    }


    private fun uploadUserImage(
        uri : Uri,
        userName : String,
    ){
        _state.value = state.value.copy(isLoading = true)
            useCases.uploadUserImageUseCase(
                uri,
                userName,
                onSuccessListener = {
                    _state.value = state.value.copy(isLoading = false, userImage = uri)
                },
                onFailureListener = {
                    _state.value = state.value.copy(isLoading = false, isError = it.localizedMessage ?:" Error occured")
                }
            )

        /*seCases.uploadUserImageUseCase(
            uri,
            userState.user.username,
        ).onEach {

            when(it){

                is Resource.Success ->{
                    _state.value = state.value.copy(isLoading = false, userImage = uri)
                }
                is Resource.Loading ->{
                    _state.value = state.value.copy()
                }
                is Resource.Error ->{
                    _state.value = state.value.copy(isLoading = false,it.message!!)
                }

            }

        }*/

    }

    private fun getUserImage(
        userState: SignedInState
    ){
        _state.value = state.value.copy(isLoading = true)
        useCases.getUserImageUseCase(
            userName = userState.user.username,
            file = File("${context.filesDir}/${userState.user.username}.jpeg"),
            onSuccessListener = {
                var inputStream = it.file.toUri()
                println("uri $inputStream")
                _state.value = state.value.copy(isLoading = false, userImage = inputStream)
            },
            onFailureListener = {
                it.localizedMessage?.let {
                    _state.value = state.value.copy(isLoading = false, isError = it)
                }?:{
                    _state.value = state.value.copy(isLoading = false, isError = "Error Occured")
                }
                println(it.localizedMessage)
            }
        )
    }

}