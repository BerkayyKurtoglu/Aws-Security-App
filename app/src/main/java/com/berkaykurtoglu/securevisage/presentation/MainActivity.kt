package com.berkaykurtoglu.securevisage.presentation

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.berkaykurtoglu.securevisage.presentation.AlertScreen.AlertScreen
import com.berkaykurtoglu.securevisage.presentation.LoginScreen.LoginScreen
import com.berkaykurtoglu.securevisage.presentation.theme.SecureVisageTheme
import com.berkaykurtoglu.securevisage.utils.ConfigureAWS
import com.berkaykurtoglu.securevisage.utils.NotificationService
import com.berkaykurtoglu.securevisage.utils.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            ConfigureAWS(applicationContext)
        }catch (e: Exception){
            e.printStackTrace()
        }

        setContent {
            SecureVisageTheme(
                dynamicColor = false
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
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
                            println("data : $data")
                            AlertScreen()
                        }
                    }

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SecureVisageTheme {
    }
}