package com.xzq.composestudy.widget

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun topBar( ) = TopAppBar(

    title = {
        Text(
            "首页",
            maxLines = 1,
            softWrap = true,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFFFFFFFF),
            modifier = Modifier.padding(start = 5.dp, end = 10.dp)
        )
    },
    // https://article.juejin.cn/post/7229150192259465274
    navigationIcon = {
        val drawerState = DrawerState(DrawerValue.Closed)
        val state = rememberScaffoldState(drawerState)
        val scope = rememberCoroutineScope()
        IconButton(
            onClick = {
                if (drawerState.currentValue == DrawerValue.Closed) {
                    Log.e("TAG", "----   42")
                    scope.launch {
                        state.drawerState.open()
//                        drawerState.animateTo(DrawerValue.Open, TweenSpec())
                    }
                } else {
                    Log.e("TAG", "----   47")
                }
            }
        ) {
            Icon(Icons.Filled.Menu, null)
        }
    },
    actions = {
        IconButton(
            onClick = {

            }
        ) {
            Icon(Icons.Default.Search, null)
        }
        IconButton(
            onClick = {}
        ) {
            Icon(Icons.Filled.Settings, null)
        }
    }
)

//Scaffold(
//scaffoldState = scaffoldState,
@Composable
fun topBar1() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text(text = "点击左边滑出菜单")
        },
        navigationIcon = {
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier.clickable {
                    scope.launch { //打开策划菜单的方法
                        scaffoldState.drawerState.open()
                    }
                })
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "删除",
                Modifier
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
                    .clickable {
                        scope.launch {
                            //关闭策划菜单的方法
                            scaffoldState.drawerState.close()
                            //显示snackBar类似Toast

                        }
                    }, tint = Color.White
            )
        })
}