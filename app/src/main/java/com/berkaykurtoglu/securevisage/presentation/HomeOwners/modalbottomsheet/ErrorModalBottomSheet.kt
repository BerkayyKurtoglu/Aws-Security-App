package com.berkaykurtoglu.securevisage.presentation.HomeOwners.modalbottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorModalSheet(
    modifier: Modifier = Modifier,
    error : String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error)
    }

}


