package com.berkaykurtoglu.securevisage.presentation

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.berkaykurtoglu.securevisage.presentation.AlertScreen.AlertScreen
import com.berkaykurtoglu.securevisage.presentation.LoginScreen.LoginScreen
import com.berkaykurtoglu.securevisage.utils.Screens

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    NavHost(navController = navController,startDestination = Screens.LoginScreen.route){
        composable(Screens.LoginScreen.route){
            LoginScreen()
        }
        composable(
            Screens.AlertScreen.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://berkayykurtoglu.github.io/{id}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
            )
        ){
            val data = it.arguments?.getString("id")
            Log.i("MainActivity","data : $data")
            AlertScreen(navController)
        }
    }


}



