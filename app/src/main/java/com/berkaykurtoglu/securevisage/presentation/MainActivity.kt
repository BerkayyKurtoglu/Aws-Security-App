package com.berkaykurtoglu.securevisage.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.berkaykurtoglu.securevisage.presentation.AlertScreen.AlertScreen
import com.berkaykurtoglu.securevisage.presentation.LoginScreen.LoginScreen
import com.berkaykurtoglu.securevisage.presentation.theme.SecureVisageTheme
import com.berkaykurtoglu.securevisage.utils.ConfigureAWS
import com.berkaykurtoglu.securevisage.utils.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureVisageTheme(
                dynamicColor = false
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConfigureAWS(applicationContext)
                    val navController = rememberNavController()
                    NavHost(navController = navController,startDestination = Screens.LoginScreen.route){
                        composable(Screens.LoginScreen.route){
                            LoginScreen()
                        }
                        composable(
                            Screens.AlertScreen.route,
                            deepLinks = listOf(

                            )
                        ){
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