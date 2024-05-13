package com.berkaykurtoglu.securevisage.presentation.HomeOwners.addnewusermodalbottom

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewUserBottomSheet(
    modifier: Modifier = Modifier,
    sheetState : SheetState,
    showBottomSheet : MutableState<Boolean>,
    uiState : State<NewUserSheetState>,
    uri : Uri?,
    usersName : MutableState<String>,
    onUploadNewUser : (String) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var bitmap : Bitmap? = null
    uri?.let {
        val b = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        bitmap = b
    }


    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState
    ) {

        Column(
            modifier = modifier
                .padding(horizontal = 30.dp, vertical = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (uiState.value.bottomSheetIsLoading) {
                CircularProgressIndicator()
            } else if (uiState.value.bottomSheetErrorMessage.isNotBlank()) {
                Text(text = uiState.value.bottomSheetErrorMessage)
            } else{
                if (uiState.value.isSuccess){
                    Text(text = "User Added Successfully")
                }else{
                    bitmap?.asImageBitmap()?.let {
                        Image(
                            bitmap = it,
                            contentDescription ="User Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(25.dp))
                        )
                    } ?: Text(text = "No Image Selected")

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = usersName.value,
                        onValueChange = {
                            usersName.value = it
                        },
                        placeholder = {
                            Text(text = "Kişinin adı")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    FilledTonalButton(
                        onClick = {
                            uri?.let {
                                onUploadNewUser(usersName.value)
                            } ?: Toast.makeText(context, "Please Select an Image", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text(text = "Kaydet")
                    }
                }

            }

        }

    }


}


