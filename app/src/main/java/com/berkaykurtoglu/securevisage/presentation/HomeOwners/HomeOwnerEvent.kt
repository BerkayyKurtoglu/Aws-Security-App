package com.berkaykurtoglu.securevisage.presentation.HomeOwners

sealed class HomeOwnerEvent {

    data object OnRetryEvent : HomeOwnerEvent()

    data object OnFirstTimeCall : HomeOwnerEvent()

    data class OnGetHomeOwnerPic(val key : String) : HomeOwnerEvent()

}