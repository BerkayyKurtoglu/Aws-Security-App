package com.berkaykurtoglu.securevisage.presentation.EntryScreen

import android.net.Uri
import com.amplifyframework.ui.authenticator.SignedInState

sealed class EntryScreenEvent {

    data class OnUploadUserImageEvent(val uri: Uri,val userName : String) : EntryScreenEvent()

    data class OnGetUserImageEvent(val userState : SignedInState) : EntryScreenEvent()

}