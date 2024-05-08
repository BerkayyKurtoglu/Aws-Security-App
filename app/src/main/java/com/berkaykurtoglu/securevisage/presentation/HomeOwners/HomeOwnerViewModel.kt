package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import androidx.lifecycle.ViewModel
import com.berkaykurtoglu.securevisage.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeOwnerViewModel @Inject constructor(
    private val useCases : UseCases
) : ViewModel() {




}