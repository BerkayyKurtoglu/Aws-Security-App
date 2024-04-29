package com.berkaykurtoglu.securevisage.presentation.AlertScreen

import android.graphics.Bitmap
import android.net.Uri

sealed class AlertScreenEvent {

    data class OnAddUserEvent(val name : String, val bitmap : Bitmap) : AlertScreenEvent()


}