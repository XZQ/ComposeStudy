package com.xzq.composestudy

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.xzq.composestudy.view.WellnessScreen

@Composable
fun rootMinePage(innerPadding: PaddingValues) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        WellnessScreen(modifier = Modifier.padding(innerPadding))
    }
}
