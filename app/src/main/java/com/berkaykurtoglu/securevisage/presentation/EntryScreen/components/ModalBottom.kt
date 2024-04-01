package com.berkaykurtoglu.securevisage.presentation.EntryScreen.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraModalBottom(
    state : SheetState,
    onDismissListener : () -> Unit,
    onPickPhotoClicked : () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = { onDismissListener() },
        sheetState = state,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedIconButton(
                    onClick = {onPickPhotoClicked()},
                    border = BorderStroke(1.dp,color = Color.LightGray),
                ){
                    Icon(
                        imageVector = Icons.Outlined.FileUpload,
                        contentDescription = "Pick Photo",
                        tint = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Yükle",
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.width(25.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedIconButton(
                    onClick = {  },
                    border = BorderStroke(1.dp,color = Color.LightGray),
                    ){
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Pick Photo",
                        tint = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.height(7.dp))
                Text(text = "Fotoğraf Çek",
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            }

        }
    }

}