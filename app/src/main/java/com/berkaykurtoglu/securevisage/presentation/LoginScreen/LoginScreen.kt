package com.berkaykurtoglu.securevisage.presentation.LoginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.amplifyframework.ui.authenticator.ui.Authenticator
import com.berkaykurtoglu.securevisage.presentation.EntryScreen.EntryScreen

@Composable
fun LoginScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        //EntryScreen()
        Authenticator {
            EntryScreen(navController,it)
        }
    }

}