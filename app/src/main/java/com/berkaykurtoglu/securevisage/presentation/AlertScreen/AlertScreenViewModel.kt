package com.berkaykurtoglu.securevisage.presentation.AlertScreen

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import com.berkaykurtoglu.securevisage.utils.bitmapToUri
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AlertScreenViewModel @Inject constructor(
    private val useCases: UseCases,
    @ApplicationContext private val context : Context
) : ViewModel() {

    private val _state = mutableStateOf(AlertScreenState())
    val state : State<AlertScreenState> = _state

    fun onEvent(event: AlertScreenEvent){
        when(event){
            is AlertScreenEvent.OnAddKnownUserEvent -> {
                addKnownUserImage(event.name,event.bitmap)
            }
            is AlertScreenEvent.OnAddUnknownUserEvent -> {
                addUnknownUserImage(event.bitmap)
            }
        }

    }

    private fun addKnownUserImage(userName: String, bitmap: Bitmap){

        _state.value = state.value.copy(isLoading = true)
        useCases.uploadUserImageUseCase(
            bitmap.bitmapToUri(context = context),
            userName,
            onSuccessListener = {
                _state.value = state.value.copy(isLoading = false, isSuccess = true)
            },
            onFailureListener = {
                _state.value = state.value.copy(isLoading = false, isSuccess = false, errorMessage = it.localizedMessage ?: "Error")
            }
        )
    }

    private fun addUnknownUserImage(
        bitmap: Bitmap
    ) {
        _state.value = state.value.copy(isLoading = true, isSuccess = false)
        useCases.uploadUnknownImageUseCase(
            bitmap.bitmapToUri(context = context),
            onSuccessListener = {
                _state.value = state.value.copy(isLoading = false, isSuccess = true)
            },
            onFailureListener = {
                _state.value = state.value.copy(isLoading = false, isSuccess = false, errorMessage = it.localizedMessage ?: "Error")
            }
        )

    }


}