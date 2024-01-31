package com.xzq.composestudy

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.xzq.composestudy.widgets.AllButtons
import com.xzq.composestudy.widgets.Chips

@Composable
fun rootDiscoverPage(innerPadding: PaddingValues) {
    Scaffold(
        modifier = Modifier.padding(innerPadding),
        topBar = {},
        content = { value ->
            WidgetScreenContent(Modifier.padding(value))
        }
    )
}

@Composable
fun WidgetScreenContent(modifier: Modifier = Modifier) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier
    ) {
        item { AllButtons() }
        item { Chips() }
    }
}