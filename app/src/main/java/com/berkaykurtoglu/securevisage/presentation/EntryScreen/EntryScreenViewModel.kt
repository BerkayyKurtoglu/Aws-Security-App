package com.berkaykurtoglu.securevisage.presentation.EntryScreen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.SignedInState
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import com.berkaykurtoglu.securevisage.utils.Resource
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

@HiltViewModel
class EntryScreenViewModel @Inject constructor (
    private val useCases : UseCases,
    @ApplicationContext var context : Context
) :ViewModel() {

    private val _state = mutableStateOf(EntryScreenState())
    val state : State<EntryScreenState> = _state

    init {
        /*Amplify.Auth.getCurrentUser(
            {
                Amplify.Notifications.Push.identifyUser(
                    it.userId,
                    {
                        println("User identified")
                    },
                    {

                    }
                )
            },
            {

            }
        )*/
    }

    fun uploadUserImage(
        uri : Uri,
        userState : SignedInState,
    ){
        _state.value = state.value.copy(isLoading = true)
            useCases.uploadUserImageUseCase(
                uri,
                userState.user.username,
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

    fun getUserImage(
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