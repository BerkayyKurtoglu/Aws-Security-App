package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String,
    onRetry: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = message
        )
        FilledTonalButton(
            onClick = onRetry
        ) {
            Text(text = "Tekrar Dene")
        }
    }

}

@Preview
@Composable
private fun ErrorScreenPrev() {
    ErrorScreen(message = "Test"){}
}



