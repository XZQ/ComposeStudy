package com.xzq.composestudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.xzq.composestudy.data.iconList
import com.xzq.composestudy.data.navList
import com.xzq.composestudy.ui.theme.ComposeStudyTheme
import com.xzq.composestudy.widget.topBar
import kotlinx.coroutines.launch

/**
 * https://juejin.cn/post/7271056651128832058?utm_source=gold_browser_extension
 * https://blog.csdn.net/qq_39312146/article/details/130664017
 * https://juejin.cn/post/7321558573518782490?utm_source=gold_browser_extension
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            ComposeHomeUI()
        }
    }
}

@Composable
fun ComposeHomeUI() {
    ProvideWindowInsets {
        ComposeStudyTheme {
            HomePage()
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun rootCompose() {

    var selectIndex by remember { mutableStateOf(0) }
    val pageState = rememberPagerState(initialPage = 0)
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color(0xffEDEDED),
        darkIcons = true,
    )

    ProvideWindowInsets {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            Scaffold(
                topBar = { topBar() },
                drawerContent = { drawerContent() },
                bottomBar = {
                    /*  BottomAppBar(
                          modifier = Modifier.height(100.dp)
                      ) {
                          navList.forEachIndexed { index, str ->
                              BottomNavigationItem(
                                  label = { Text(str) },
                                  selected = index == selectIndex,
                                  onClick = { selectIndex = index },
                                  icon = { Icon(imageVector = iconList[index], contentDescription = null) },
                                  selectedContentColor = colorResource(id = R.color.gray_10),
                                  unselectedContentColor = colorResource(id = R.color.white)
                              )
                          }
                      }*/
                    BottomAppBar(
                        contentColor = Color(ContextCompat.getColor(context, if (selectIndex > 1) R.color.white else R.color.nav_bg)),
                    ) {
                        navList.forEachIndexed { index, str ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clickable {
                                        selectIndex = index
                                        scope.launch {
                                            pageState.scrollToPage(index)
                                        }
                                    }
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Icon(
                                        imageVector = iconList[index],
                                        contentDescription = null,
                                        tint = Color(
                                            if (selectIndex == index) ContextCompat.getColor(
                                                context,
                                                R.color.green
                                            ) else ContextCompat.getColor(context, R.color.gray)
                                        )
                                    )
                                    Text(
                                        text = str,
                                        fontSize = 12.sp,
                                        color = Color(
                                            if (selectIndex == index) ContextCompat.getColor(
                                                context,
                                                R.color.green
                                            )
                                            else ContextCompat.getColor(context, R.color.gray)
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            ) { innerPadding ->
                when (selectIndex) {
                    0 -> rootMainPage(innerPadding, onChangeVisible = {})
                    1 -> rootDiscoverPage(innerPadding)
                    2 -> rootFriendPage(innerPadding)
                    3 -> rootMinePage(innerPadding)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeStudyTheme {
        Greeting("Android")
    }
}