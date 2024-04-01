package com.berkaykurtoglu.securevisage.presentation.EntryScreen

import android.net.Uri

data class EntryScreenState(

    var isLoading : Boolean = true,
    var isError : String = "",
    var userImage : Uri? = null

)
