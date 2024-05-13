package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.berkaykurtoglu.securevisage.presentation.HomeOwners.addnewusermodalbottom.NewUserBottomSheet
import com.berkaykurtoglu.securevisage.presentation.HomeOwners.detailsmodalbottomsheet.CustomBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeOwnersScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeOwnerViewModel = hiltViewModel()
) {

    val lazyListState = rememberLazyListState()
    val uiState = remember {
        viewModel.uiState
    }
    val sheetState = remember{
        viewModel.detailsModalBottomState
    }
    val customBottomSheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember {
        mutableStateOf(false)
    }

    val newUserSheetState = rememberModalBottomSheetState()
    val newUserSheetShow = remember {
        mutableStateOf(false)
    }
    val newUserSheetUiState = remember {
        viewModel.newUserSheetState
    }

    //create bitmap mutable state
    val uri = remember {
        mutableStateOf<Uri?>(null)
    }
    val newUserName = remember {
        mutableStateOf("")
    }
    
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ){
        it?.let {
            newUserSheetShow.value = true
            uri.value = it
        }
    }


    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(HomeOwnerEvent.OnFirstTimeCall)
    }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                photoPickerLauncher=photoPickerLauncher
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (uiState.value.pageIsLoading){
                CircularProgressIndicator()
            }else if(uiState.value.errorMessage.isNotBlank()){
                ErrorScreen(message = uiState.value.errorMessage){
                    viewModel.onEvent(HomeOwnerEvent.OnRetryEvent)
                }
            }else{
                UserLazyList(lazyListState = lazyListState, userList = uiState.value.userList) {
                    viewModel.onEvent(HomeOwnerEvent.OnGetHomeOwnerPic(it))
                    showBottomSheet.value = true
                }
                if(showBottomSheet.value){
                    CustomBottomSheet(
                        sheetState = customBottomSheetState,
                        showBottomSheet = showBottomSheet,
                        uiState = sheetState
                    )
                }
                if(newUserSheetShow.value){
                    NewUserBottomSheet(
                        sheetState = newUserSheetState,
                        showBottomSheet = newUserSheetShow,
                        uiState = newUserSheetUiState,
                        uri = uri.value,
                        usersName = newUserName,
                        onUploadNewUser = {
                            viewModel.onEvent(HomeOwnerEvent.OnUploadNewUser(it,uri.value!!))
                        }
                    )
                }

            }
        }
    }

}



