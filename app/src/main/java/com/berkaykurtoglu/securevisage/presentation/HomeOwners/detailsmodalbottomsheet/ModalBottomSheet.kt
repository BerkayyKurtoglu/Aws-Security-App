package com.berkaykurtoglu.securevisage.presentation.HomeOwners.detailsmodalbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.berkaykurtoglu.securevisage.utils.uriToBitmap


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    modifier: Modifier = Modifier,
    sheetState : SheetState,
    showBottomSheet : MutableState<Boolean>,
    uiState : State<DetailsModalBottomState>
) {

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState,
    ) {

        HorizontalDivider(
            thickness = 0.5.dp
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if(uiState.value.bottomSheetIsLoading){
                CircularProgressIndicator()
            }else if (uiState.value.bottomSheetImage == null){
                ErrorModalSheet(error = uiState.value.bottomSheetErrorMessage)
            }else{
                Image(
                    bitmap = uiState.value.bottomSheetImage!!.uriToBitmap(LocalContext.current.applicationContext),
                    contentDescription = "",
                    modifier = Modifier.size(150.dp).clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

}


