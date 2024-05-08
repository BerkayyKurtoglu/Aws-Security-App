package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

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
    val customBottomSheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.OnEvent(HomeOwnerEvent.OnFirstTimeCall)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.value.isLoading){
            CircularProgressIndicator()
        }else if(uiState.value.errorMessage.isNotBlank()){
            ErrorScreen(message = uiState.value.errorMessage){
                viewModel.OnEvent(HomeOwnerEvent.OnRetryEvent)
            }
        }else{
            UserLazyList(lazyListState = lazyListState, userList = uiState.value.userList) {
                showBottomSheet.value = true
            }
            if(showBottomSheet.value){
                // todo : add bottom sheet
                CustomBottomSheet(
                    sheetState = customBottomSheetState,
                    showBottomSheet = showBottomSheet
                )
            }
        }
    }

}



