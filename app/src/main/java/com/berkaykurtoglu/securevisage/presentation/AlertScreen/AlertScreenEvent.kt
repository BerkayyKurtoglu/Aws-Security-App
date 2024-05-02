package com.berkaykurtoglu.securevisage.presentation.AlertScreen

import android.graphics.Bitmap

sealed class AlertScreenEvent {

    data class OnAddKnownUserEvent(val name : String, val bitmap : Bitmap) : AlertScreenEvent()
    data class OnAddUnknownUserEvent(val bitmap : Bitmap) : AlertScreenEvent()

}