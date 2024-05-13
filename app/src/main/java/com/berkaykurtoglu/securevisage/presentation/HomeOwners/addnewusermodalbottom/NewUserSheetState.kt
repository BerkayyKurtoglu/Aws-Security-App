package com.berkaykurtoglu.securevisage.presentation.HomeOwners.addnewusermodalbottom

import android.net.Uri

data class NewUserSheetState(
    val bottomSheetIsLoading: Boolean = false,
    val bottomSheetErrorMessage: String = "",
    val isSuccess : Boolean = false,
)