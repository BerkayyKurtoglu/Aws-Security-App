package com.berkaykurtoglu.securevisage.presentation.EntryScreen

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Dimension
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.SignedInState
import com.berkaykurtoglu.securevisage.R
import com.berkaykurtoglu.securevisage.presentation.EntryScreen.components.CameraModalBottom
import com.berkaykurtoglu.securevisage.presentation.LoginScreen.LoginScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun EntryScreen(
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
        viewModel.getUserImage(signedInState)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let {
            viewModel.uploadUserImage(
                uri = it,
                signedInState
            )
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
                viewModel.uploadUserImage(
                    uri = uri,
                    signedInState
                )
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
                            text = signedInState.user.username,
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
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(state.userImage)
                                    .error(R.drawable.icon_face_id)
                                    .size(500,500)
                                    .build(),
                                contentDescription = "User Image",
                                loading = {
                                    CircularProgressIndicator()
                                }
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


