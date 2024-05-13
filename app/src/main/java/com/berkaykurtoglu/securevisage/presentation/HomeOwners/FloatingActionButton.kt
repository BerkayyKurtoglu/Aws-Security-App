package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CustomFloatingActionButton(
    modifier: Modifier = Modifier,
    photoPickerLauncher : ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {


    FloatingActionButton(
        modifier = modifier,
        onClick = {
        /*TODO : Take persons picture*/
            photoPickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {

        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Upload Image"
        )

    }

}



