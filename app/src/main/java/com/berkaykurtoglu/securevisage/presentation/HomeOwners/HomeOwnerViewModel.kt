package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import com.berkaykurtoglu.securevisage.presentation.HomeOwners.modalbottomsheet.ModalBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeOwnerViewModel @Inject constructor(
    private val useCases : UseCases,
    @ApplicationContext private val context : Context
) : ViewModel() {


    private val _uiState = mutableStateOf(HomeOwnerState())
    val uiState : State<HomeOwnerState> = _uiState

    private val _sheetState = mutableStateOf(ModalBottomSheetState())
    val sheetState : State<ModalBottomSheetState> = _sheetState

    fun onEvent(event : HomeOwnerEvent){
        when(event){
            is HomeOwnerEvent.OnRetryEvent -> {
                retryGetHomeOwnersList()
            }
            is HomeOwnerEvent.OnFirstTimeCall ->{
                getHomeOwnersList()
            }
            is HomeOwnerEvent.OnGetHomeOwnerPic ->{
                getHomeOwnerPicture(event.key)
            }
        }
    }

    private fun getHomeOwnerPicture(
        key : String
    ){
        _sheetState.value = sheetState.value.copy(bottomSheetIsLoading = true, bottomSheetImage = null)
        useCases.getHomeOwnersPicture(
            key,
            file = File("${context.filesDir}/${key}.jpeg"),
            {
                _sheetState.value = sheetState.value.copy(bottomSheetIsLoading = false, bottomSheetImage = it.file.toUri())
            },
            {
                _sheetState.value = sheetState.value.copy(
                    bottomSheetIsLoading = false,
                    bottomSheetImage = null,
                    bottomSheetErrorMessage = it.localizedMessage ?: "Error"
                )
            }
        )
    }

    private fun getHomeOwnersList(){
        _uiState.value = uiState.value.copy(pageIsLoading = true)
        useCases.getHomeOwnerList(
            {
                val names = it.items.map {item->
                    item.path.substringAfter("/").substringAfter("/")
                }
                _uiState.value = uiState.value.copy(pageIsLoading = false, userList = names)
            },
            {
                _uiState.value = uiState.value.copy(pageIsLoading = false, errorMessage = it.localizedMessage ?: "Error")
            }
        )
    }

    private fun retryGetHomeOwnersList(){
        resetState()
        getHomeOwnersList()
    }

    private fun resetState(){
        _uiState.value = HomeOwnerState()
    }


}