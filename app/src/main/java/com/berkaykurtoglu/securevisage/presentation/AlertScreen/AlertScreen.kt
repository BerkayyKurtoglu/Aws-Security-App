package com.berkaykurtoglu.securevisage.presentation.AlertScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.berkaykurtoglu.securevisage.utils.Screens

@Composable
fun AlertScreen(
    navHostController: NavHostController,
    viewModel: AlertScreenViewModel = hiltViewModel(),
    base64Image : String?
){

    val state by remember {
        viewModel.state
    }

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
            modifier = Modifier.fillMaxWidth().animateContentSize()
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
                if(isButtonVisible){
                    Spacer(modifier = Modifier.height(15.dp))
                    if (state.isLoading){
                        CircularProgressIndicator()
                    }else if (state.isSuccess){
                        navHostController.navigate(route = Screens.LoginScreen.route){
                            popUpTo(Screens.LoginScreen.route)
                        }
                        Toast.makeText(LocalContext.current, "Alarm tetiklenmek üzeri bildirildi", Toast.LENGTH_LONG).show()
                    }else if(state.errorMessage.isNotBlank()){
                        Text(text = state.errorMessage)
                    }else{
                        OutlinedButton(
                            onClick = {
                                viewModel.onEvent(AlertScreenEvent.OnAddUnknownUserEvent(bitmap = decodedImage))
                            },
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
                            modifier = Modifier.padding(top = 20.dp),
                            placeholder = { Text(text = "İsim")},
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (state.isLoading){
                                CircularProgressIndicator()
                            }else if (state.isSuccess){
                                navHostController.navigate(route = Screens.LoginScreen.route){
                                    popUpTo(Screens.LoginScreen.route)
                                }
                                Toast.makeText(LocalContext.current, "Kullanıcı Eklendi", Toast.LENGTH_LONG).show()
                            }else if(state.errorMessage.isNotBlank()){
                                Text(text = state.errorMessage)
                            }
                            else{
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
                                    onClick = {
                                        Log.i("AlertScreen","Working")
                                        viewModel.onEvent(AlertScreenEvent.OnAddKnownUserEvent(
                                            textFieldString,
                                            decodedImage
                                        ))
                                    },
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
    
    
}