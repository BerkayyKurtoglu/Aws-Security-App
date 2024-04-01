package com.berkaykurtoglu.securevisage.presentation.EntryScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amplifyframework.ui.authenticator.SignedInState
import com.berkaykurtoglu.securevisage.R
import com.berkaykurtoglu.securevisage.presentation.EntryScreen.components.CameraModalBottom
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen(
    state : SignedInState
) {

    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val showBottomSheet = remember {
        mutableStateOf(false)
    }
    val selectedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        selectedImageUri.value = it
        scope.launch() {
            bottomSheetState.hide()
        }
        showBottomSheet.value = false
    }


    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), start = 25.dp, end = 25.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.secure_visage),
                contentDescription = "logo",
                modifier  = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 25.dp)
            )
            ElevatedCard(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .wrapContentSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = state.user.username,
                        modifier = Modifier
                            .basicMarquee() ,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ElevatedCard(
                        modifier = Modifier
                            .clickable {
                                scope.launch { showBottomSheet.value = true }
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_face_id),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(30.dp)
                        )
                    }
                }
                /*Box(
                    modifier = Modifier.fillMaxSize()
                ) {

                }
                /*Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.face_icon),
                        contentDescription = "",
                        modifier = Modifier.padding(bottom = 80.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            state.signOut()
                        }
                    }) {
                        Text(text = "Sign Out")
                    }
                }*/*/
            }

        }

        if (showBottomSheet.value) {
            CameraModalBottom(
                state = bottomSheetState,
                onDismissListener = {
                    showBottomSheet.value = false
                },
                onPickPhotoClicked = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        }
    }

}


