package com.berkaykurtoglu.securevisage.presentation.HomeOwners.modalbottomsheet

import android.net.Uri
import java.net.URL

data class ModalBottomSheetState(

    val bottomSheetImage : Uri? = null,
    val bottomSheetIsLoading : Boolean = false,
    val bottomSheetErrorMessage : String = ""

)