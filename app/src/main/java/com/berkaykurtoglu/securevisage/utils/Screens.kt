package com.berkaykurtoglu.securevisage.utils

sealed class Screens (
    val route : String
) {

    data object LoginScreen : Screens("login_screen")
    data object AlertScreen : Screens("alert_screen")

}