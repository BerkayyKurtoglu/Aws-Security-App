package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.berkaykurtoglu.securevisage.presentation.HomeOwners.modalbottomsheet.CustomBottomSheet

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
        viewModel.sheetState
    }
    val customBottomSheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember {
        mutableStateOf(false)
    }

    
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ){
        Log.v("Photo Pick - HomeOwnersScreen", "Photo Uri : $it")
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
            }
        }
    }

}



