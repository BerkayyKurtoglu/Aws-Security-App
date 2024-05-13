package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import com.berkaykurtoglu.securevisage.presentation.HomeOwners.addnewusermodalbottom.NewUserSheetState
import com.berkaykurtoglu.securevisage.presentation.HomeOwners.detailsmodalbottomsheet.DetailsModalBottomState
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

    private val _detailsSheetState = mutableStateOf(DetailsModalBottomState())
    val detailsModalBottomState : State<DetailsModalBottomState> = _detailsSheetState

    private val _newUserSheetState = mutableStateOf(NewUserSheetState())
    val newUserSheetState : State<NewUserSheetState> = _newUserSheetState

    val usersName = mutableStateOf("")

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
            is HomeOwnerEvent.OnUploadNewUser -> {
                addNewUser(event.name, event.uri)
            }
        }
    }

    private fun addNewUser(
        name : String,
        uri : Uri
    ){
        _newUserSheetState.value = newUserSheetState.value.copy(
            bottomSheetIsLoading = true
        )
        useCases.uploadUserImageUseCase(
            uri,
            name,
            {
                _newUserSheetState.value = newUserSheetState.value.copy(
                    bottomSheetIsLoading = false,
                    bottomSheetErrorMessage = "",
                    isSuccess = true
                )
            },
            {
                _newUserSheetState.value = NewUserSheetState(
                    bottomSheetIsLoading = false,
                    bottomSheetErrorMessage = it.localizedMessage ?: "Error",
                    isSuccess = false
                )
            }
        )

    }

    private fun getHomeOwnerPicture(
        key : String
    ){
        _detailsSheetState.value = detailsModalBottomState.value.copy(bottomSheetIsLoading = true, bottomSheetImage = null)
        useCases.getHomeOwnersPicture(
            key,
            file = File("${context.filesDir}/${key}.jpeg"),
            {
                _detailsSheetState.value = detailsModalBottomState.value.copy(bottomSheetIsLoading = false, bottomSheetImage = it.file.toUri())
            },
            {
                _detailsSheetState.value = detailsModalBottomState.value.copy(
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