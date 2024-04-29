package com.berkaykurtoglu.securevisage.presentation.AlertScreen

data class AlertScreenState(

    var isLoading : Boolean = false,
    var errorMessage : String = "",
    var isSuccess : Boolean = false

)