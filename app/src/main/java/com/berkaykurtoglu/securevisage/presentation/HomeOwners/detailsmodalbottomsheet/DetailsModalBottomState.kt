package com.berkaykurtoglu.securevisage.presentation.HomeOwners.detailsmodalbottomsheet

import android.net.Uri

data class DetailsModalBottomState(

    val bottomSheetImage : Uri? = null,
    val bottomSheetIsLoading : Boolean = false,
    val bottomSheetErrorMessage : String = ""

)