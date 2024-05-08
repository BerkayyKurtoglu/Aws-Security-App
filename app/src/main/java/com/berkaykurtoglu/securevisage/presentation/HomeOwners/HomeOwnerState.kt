package com.berkaykurtoglu.securevisage.presentation.HomeOwners

data class HomeOwnerState(

    val userList: List<String> = mutableListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""

)