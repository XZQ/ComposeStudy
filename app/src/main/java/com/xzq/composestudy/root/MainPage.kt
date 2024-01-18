package com.xzq.composestudy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.xzq.composestudy.main.MainViewModel
import com.xzq.composestudy.widgets.nestScroll

@Composable
fun rootMainPage(
    innerPadding: PaddingValues,
    viewModel: MainViewModel = MainViewModel(),
    onChangeVisible: (Boolean) -> Unit
) {
    val context = LocalContext.current

    /*** 获取状态栏高度 */
    val statusBarHeight = LocalDensity.current.run {
        WindowInsets.statusBars.getTop(this).toDp()
    }
    val scrollState = rememberLazyListState()

    var offset by remember { mutableStateOf(0f) }

    val fullHeight = with(LocalContext.current) {
        resources.displayMetrics.heightPixels
    }
    val density = LocalDensity.current.density

    Column(modifier = Modifier.fillMaxSize()) {
        nestScroll()
    }
}

@Composable
private fun autoIncrement(modifier: Modifier) {
    val count = remember { mutableStateOf(0) }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${count.value}", modifier = Modifier)
        Button(
            onClick = { count.value++ },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(Color.Transparent),
            border = BorderStroke(2.dp, Color.White),//边框颜色
//            BorderStroke(
//                10.dp,
//                Brush.radialGradient(listOf(Color.White, Color.Black))
//            )
            enabled = true,
//            shape = CutCornerShape(30),
//            contentPadding = PaddingValues(100.dp)
        ) {
            Text(text = "Add")
        }
    }
}

