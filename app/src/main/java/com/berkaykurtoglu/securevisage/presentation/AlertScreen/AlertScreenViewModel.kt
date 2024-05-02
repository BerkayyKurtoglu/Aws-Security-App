package com.berkaykurtoglu.securevisage.presentation.AlertScreen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import com.berkaykurtoglu.securevisage.utils.bitmapToUri
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
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
            is AlertScreenEvent.OnAddUserEvent -> {
                addUserImage(event.name,event.bitmap)
            }
        }

    }

    private fun addUserImage(userName: String, bitmap: Bitmap){

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

    private fun bitmapToUri(bitmap: Bitmap): Uri {
        Log.i("AlertViewModel","Working")
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }


}