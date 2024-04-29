package com.berkaykurtoglu.securevisage.presentation.AlertScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AlertScreen(
    base64Image : String? = "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"
){

    var replaced = ""
    base64Image?.let {
        replaced = it.replace("_","/")
    }

    var isTextFieldVisible by remember {
        mutableStateOf(false)
    }
    var textFieldString by remember {
        mutableStateOf("")
    }
    var isButtonVisible by remember {
        mutableStateOf(true)
    }
    val imageBytes = Base64.decode(replaced, Base64.DEFAULT)
    val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = "Bu kişiyi tanıyor musunuz ?",
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(15.dp))
                Image(

                    bitmap = Bitmap.createScaledBitmap(decodedImage, 450, 350, true).asImageBitmap(),
                    contentDescription = "Tanınmayan Kişi",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp))
                )
                AnimatedVisibility(visible = isButtonVisible) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(15.dp))
                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                        ) {
                            Text(
                                text = "Tanımıyorum !",
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = {
                                isTextFieldVisible = true
                                isButtonVisible = !isButtonVisible
                            }
                        ) {
                            Text(text = "Tanıyorum")
                        }
                    }
                }
                AnimatedVisibility(visible = isTextFieldVisible) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = textFieldString,
                            onValueChange = {
                                textFieldString = it
                            },
                            modifier = Modifier.padding(top = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            OutlinedButton(
                                onClick = {
                                    isTextFieldVisible = false
                                    isButtonVisible = true
                                },
                                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.error)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Cancel,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            OutlinedButton(
                                onClick = { /*TODO*/ },
                                border = BorderStroke(0.5.dp, Color.Gray)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }

            }
        }
        
    }
    
    
}