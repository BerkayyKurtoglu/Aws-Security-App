package com.berkaykurtoglu.securevisage.presentation.EntryScreen

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.SignedInState
import com.berkaykurtoglu.securevisage.R
import com.berkaykurtoglu.securevisage.presentation.EntryScreen.components.CameraModalBottom
import com.berkaykurtoglu.securevisage.utils.Screens
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun EntryScreen(
    navController: NavController,
    signedInState : SignedInState,
    viewModel: EntryScreenViewModel = hiltViewModel(),
    context : Context = LocalContext.current
) {


    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val showBottomSheet = remember {
        mutableStateOf(false)
    }
    val state by remember {
        viewModel.state
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(EntryScreenEvent.OnGetUserImageEvent(signedInState))
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let {
            viewModel.onEvent(EntryScreenEvent.OnUploadUserImageEvent(it,signedInState.user.username))
        } ?: {
            Toast.makeText(context, "an error occured, please try again", Toast.LENGTH_LONG).show()
        }
        showBottomSheet.value = false
    }
    val pictureResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == RESULT_OK){
            it.data?.extras?.let {
                val bytes = ByteArrayOutputStream()
                var bitmap = it.get("data") as Bitmap
                bitmap.compress(Bitmap.CompressFormat.PNG,100,bytes)
                val path = MediaStore.Images.Media.insertImage(context.contentResolver,bitmap,signedInState.user.username,null)
                val uri = Uri.parse(path)
                viewModel.onEvent(EntryScreenEvent.OnUploadUserImageEvent(uri,signedInState.user.username))
            } ?: {
                Toast.makeText(context, "an error occured, please try again", Toast.LENGTH_LONG).show()
            }
            showBottomSheet.value = false
        }
    }
    val requestCameraPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        if (it){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra("outputFormat", android.graphics.Bitmap.CompressFormat.JPEG)
            pictureResultLauncher.launch(intent)
        }else{
            // TODO : permission denied
        }
    }

    //viewModel.getUserImage(signedInState)

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
            if (state.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }else{
                ElevatedCard(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp, horizontal = 45.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = signedInState.user.username,
                            modifier = Modifier
                                .basicMarquee() ,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(state.userImage)
                                .error(R.drawable.icon_face_id)
                                .build(),
                            contentDescription = "User Image",
                            loading = {
                                CircularProgressIndicator()
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable {
                                    showBottomSheet.value = true
                                }
                                .size(250.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            FilledTonalButton(
                                onClick = { /*TODO : get history*/ },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.History,
                                    contentDescription = "History Icon"
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            FilledTonalButton(
                                onClick = {
                                          /*TODO : see the members pictures */
                                          navController.navigate(Screens.HomeOwnersScreen.route)
                                          },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Home,
                                    contentDescription = "Home Icon"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        FilledTonalButton(
                            onClick = {
                                      Amplify.Auth.signOut {
                                      }
                                      },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Logout,
                                contentDescription = "Log Out Button"
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
                },
                onOpenCameraClicked = {
                    requestCameraPermission.launch(
                        android.Manifest.permission.CAMERA
                    )
                }
            )
        }
    }

}


