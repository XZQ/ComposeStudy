package com.xzq.composestudy

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.xzq.composestudy.cupcake.ui.StartOrderPreview
import com.xzq.composestudy.main.iconList
import com.xzq.composestudy.main.navList
import com.xzq.composestudy.rally.Overview
import com.xzq.composestudy.rally.RallyDestination
import com.xzq.composestudy.rally.rallyTabRowScreens
import com.xzq.composestudy.rally.ui.components.RallyTabRow
import com.xzq.composestudy.rally.ui.theme.RallyTheme
import com.xzq.composestudy.ui.theme.ComposeStudyTheme
import com.xzq.composestudy.widgets.topBar
import kotlinx.coroutines.launch

/**
 * https://juejin.cn/post/7271056651128832058?utm_source=gold_browser_extension
 * https://blog.csdn.net/qq_39312146/article/details/130664017
 * https://juejin.cn/post/7321558573518782490?utm_source=gold_browser_extension
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

@Composable
fun ComposeCupcakeThemeUI() {
    ProvideWindowInsets {
        StartOrderPreview()
    }
}
@Composable
fun RallyApp() {
    ProvideWindowInsets {
        RallyTheme {
            var currentScreen: RallyDestination by remember { mutableStateOf(Overview) }
            Scaffold(
                topBar = {
                    RallyTabRow(
                        allScreens = rallyTabRowScreens,
                        onTabSelected = { screen -> currentScreen = screen },
                        currentScreen = currentScreen
                    )
                }
            ) { innerPadding ->
                Box(Modifier.padding(innerPadding)) {
                    currentScreen.screen()
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun rootCompose() {

    var selectIndex by remember { mutableIntStateOf(0) }
    val pageState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) { 0 }
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
                drawerContent = { },
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
                    0 -> rootMainPage(innerPadding, onChangeVisible = {}, openDrawer = {})
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