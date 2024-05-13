package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import android.net.Uri

sealed class HomeOwnerEvent {

    data object OnRetryEvent : HomeOwnerEvent()

    data object OnFirstTimeCall : HomeOwnerEvent()

    data class OnGetHomeOwnerPic(val key : String) : HomeOwnerEvent()

    data class OnUploadNewUser(
        val name : String,
        val uri : Uri
    ) : HomeOwnerEvent()


}