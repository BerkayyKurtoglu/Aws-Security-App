package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import java.net.URL

data class HomeOwnerState(

    val userList: List<String> = mutableListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val selectedUserImage : URL? = null

)