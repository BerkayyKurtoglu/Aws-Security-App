package com.berkaykurtoglu.securevisage.presentation.HomeOwners

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.berkaykurtoglu.securevisage.presentation.HomeOwners.personitem.PersonItem

@Composable
fun UserLazyList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    userList: List<String>,
    onUserClicked: (person : String) -> Unit
) {

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(userList.map { it.substringBeforeLast(".")}){
            PersonItem(
                person = it,
                onUserClicked = onUserClicked
            )
        }
    }

}



