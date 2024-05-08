package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import java.net.URL

data class HomeOwnerState(

    val userList: List<String> = mutableListOf(),
    val pageIsLoading: Boolean = false,
    val errorMessage: String = "",

)