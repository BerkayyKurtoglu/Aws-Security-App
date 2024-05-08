package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.berkaykurtoglu.securevisage.presentation.HomeOwners.personitem.PersonItem

@Composable
fun HomeOwnersScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeOwnerViewModel = hiltViewModel()
) {

    val lazyListState = rememberLazyListState()
    val uiState = remember {
        viewModel.uiState
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

            }
        }
    }


}



