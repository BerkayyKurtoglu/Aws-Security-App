package com.berkaykurtoglu.securevisage.presentation.EntryScreen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.ui.authenticator.SignedInState
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import com.berkaykurtoglu.securevisage.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryScreenViewModel @Inject constructor (
    private val useCases : UseCases
) :ViewModel() {

    private val _state = mutableStateOf(EntryScreenState())
    val state : State<EntryScreenState> = _state

    fun uploadUserImage(
        uri : Uri,
        userState : SignedInState
    ){
        println("viewmodel woring")
            val result = useCases.uploadUserImageUseCase(uri,userState.user.username)
            when(result){

                is Resource.Success ->{
                    _state.value = state.value.copy(isLoading = false, userImage = uri)
                }
                is Resource.Loading ->{
                    _state.value = state.value.copy()
                }
                is Resource.Error ->{
                    _state.value = state.value.copy(isLoading = false, result.message!!)
                }

                null -> TODO()
            }
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

}