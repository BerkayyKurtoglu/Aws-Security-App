package com.berkaykurtoglu.securevisage.presentation.AlertScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlertScreenViewModel @Inject constructor(
    useCases: UseCases
) : ViewModel() {

    private val _state = mutableStateOf(AlertScreenState())
    val state : State<AlertScreenState> = _state


}