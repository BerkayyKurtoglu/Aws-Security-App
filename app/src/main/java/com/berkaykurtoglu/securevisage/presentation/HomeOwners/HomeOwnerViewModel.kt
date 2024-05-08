package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeOwnerViewModel @Inject constructor(
    private val useCases : UseCases
) : ViewModel() {


    private val _uiState = mutableStateOf(HomeOwnerState())
    val uiState : State<HomeOwnerState> = _uiState

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
        _uiState.value = uiState.value.copy(isLoading = true)
        useCases.getHomeOwnersPicture(
            key,
            {
                _uiState.value  = uiState.value.copy(isLoading = false, selectedUserImage = it.url)
            },
            {
                _uiState.value = uiState.value.copy(isLoading = false, errorMessage = it.localizedMessage ?: "Error")
            }
        )
    }

    private fun getHomeOwnersList(){
        _uiState.value = uiState.value.copy(isLoading = true)
        useCases.getHomeOwnerList(
            {
                val names = it.items.map {item->
                    item.path.substringAfter("/").substringAfter("/")
                }
                _uiState.value = uiState.value.copy(isLoading = false, userList = names)
            },
            {
                _uiState.value = uiState.value.copy(isLoading = false, errorMessage = it.localizedMessage ?: "Error")
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