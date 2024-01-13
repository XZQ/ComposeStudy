package com.xzq.composestudy

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xzq.composestudy.ui.launchedEffect

@Composable
fun rootMinePage(innerPadding: PaddingValues) {
    launchedEffect()
}

// https://blog.csdn.net/qq_31339141/article/details/124227120
// https://zhuanlan.zhihu.com/p/619110976
// https://blog.csdn.net/Bluechalk/article/details/128064145
// https://www.cnblogs.com/guanxinjing/p/17603239.html
@Composable
fun APage() {
    val context1 = LocalContext.current as MainActivity
    val isEnabled = remember { mutableStateOf(true) }
    val buttonColors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Red,
        contentColor = Color.Yellow,
        disabledBackgroundColor = Color.DarkGray,
        disabledContentColor = Color.Black
    )

    //点击状态来源
    val interactionSource = remember { MutableInteractionSource() }
    //interactionSource.collectIsPressedAsState() 为按下状态
    //interactionSource.collectIsFocusedAsState() 为焦点选中状态
    //interactionSource.collectIsHoveredAsState() 为鼠标悬停状态
    //interactionSource.collectIsDraggedAsState() 为拖动状态

    val buttonColors1 = if (interactionSource.collectIsPressedAsState().value) {
        ButtonDefaults.buttonColors(
            backgroundColor = Color.Gray,
            contentColor = Color.White,
        )
    } else {
        ButtonDefaults.buttonColors(
            backgroundColor = Color.Cyan,
            contentColor = Color.Black,
        )
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                Toast.makeText(context1, "点击按钮", Toast.LENGTH_SHORT).show()
            },
            enabled = isEnabled.value,
            colors = buttonColors,
            border = BorderStroke(2.dp, Brush.linearGradient(listOf(Color(0xFF005599), Color(0xFF3FFFED)))),//渐变
            elevation = ButtonDefaults.elevation(defaultElevation = 10.dp)//阴影
        ) {
            Text(text = "点击")
        }
        Button(
            onClick = { isEnabled.value = !isEnabled.value },
            border = BorderStroke(1.dp, Color.Green),
            colors = buttonColors1,
            interactionSource = interactionSource
        ) {
            Text(text = "是否可用")
        }

        val isChecked = remember { mutableStateOf(true) }

        Row(modifier = Modifier.fillMaxSize()) {
            TextButton(onClick = {   }) {
                Text(text = "点击")
            }
            OutlinedButton(onClick = {   }) {
                Text(text = "点击", color = Color.Red)
            }
            IconToggleButton(checked = isChecked.value, onCheckedChange = {
                isChecked.value = it
            }) {
                Icon(
                    imageVector = if (isChecked.value) Icons.Default.CheckCircle else Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.Cyan
                )
            }
        }
    }
}