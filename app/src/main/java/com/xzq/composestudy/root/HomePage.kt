package com.xzq.composestudy

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.xzq.composestudy.data.navList
import kotlinx.coroutines.launch
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import com.xzq.composestudy.data.iconList


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage() {

    val context = LocalContext.current

    var selectIndex by rememberSaveable { mutableStateOf(0) }
    val pageState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(true) }

    val systemUiController = rememberSystemUiController().apply {
        setSystemBarsColor(
            color = Color(0xffEDEDED),
            darkIcons = true,
        )
    }

    Scaffold(
        topBar = {
            if (selectIndex != 0) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            navList[selectIndex],
                            maxLines = 1,
                            fontSize = 22.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    actions = { getCenterAlignedTopAppBarActions(context, selectIndex) },

                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = getDefaultColor(context, selectIndex),
                        scrolledContainerColor = getDefaultColor(context, selectIndex),
                        navigationIconContentColor = Color.White,
                        titleContentColor = getBlack10(context),
                        actionIconContentColor = getBlack10(context),
                    )
                )
            } else {
                Spacer(modifier = Modifier.size(10.dp))
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                BottomAppBar(
                    backgroundColor = Color(0xffEDEDED),
                 contentColor = Color(ContextCompat.getColor(context, if (selectIndex > 1) R.color.black else R.color.nav_bg)),
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
                                    tint = getTintColor(context, index, selectIndex)
                                )
                                Text(
                                    text = str,
                                    fontSize = 12.sp,
                                    color = getTintColor(context, index, selectIndex)
                                )
                            }
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        Box {
            HorizontalPager(
                count = 4,
                state = pageState,
                contentPadding = PaddingValues(horizontal = 0.dp),
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = visible
            ) { page ->
                when (page) {
                    0 -> rootMainPage(innerPadding, onChangeVisible = { v ->
                        visible = !v
                        systemUiController.setSystemBarsColor(
                            color = if (visible) Color(0xffEDEDED) else Color(0xff1B1B2B),
                            darkIcons = true,
                        )
                    })
                    1 -> rootDiscoverPage(innerPadding)
                    2 -> rootFriendPage(innerPadding)
                    3 -> rootMinePage(innerPadding)
                }
            }
            LaunchedEffect(pageState) {
                snapshotFlow { pageState.currentPage }.collect { page ->
                    selectIndex = page
                    /** 动态设置状态栏颜色 */
                    visible = true
                    systemUiController.setSystemBarsColor(
                        color = if (page != 3) Color(0xffEDEDED) else Color.White,
                        darkIcons = true,
                    )
                }
            }
        }
    }
}

@Composable
private fun getCenterAlignedTopAppBarActions(context: Context, selectIndex: Int) {
    if (selectIndex != 3) {
        IconButton(
            onClick = { }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = getBlack10(context)
            )
        }
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = getBlack10(context)
            )
        }
    }
}

private fun getTintColor(context: Context, index: Int, selectIndex: Int) = Color(
    if (selectIndex == index) {
        ContextCompat.getColor(context, R.color.green)
    } else {
        ContextCompat.getColor(context, R.color.gray)
    }
)

private fun getDefaultColor(context: Context, selectIndex: Int) = Color(
    ContextCompat.getColor(
        context,
        if (selectIndex != 3) R.color.nav_bg else R.color.white
    )
)

private fun getBlack10(context: Context) = Color(
    ContextCompat.getColor(
        context,
        R.color.black_10
    )
)

/* navList.forEachIndexed { index, str ->
                             BottomNavigationItem(
                                 label = { Text(str) },
                                 selected = index == selectIndex,
                                 onClick = { selectIndex = index },
                                 icon = { Icon(imageVector = iconList[index], contentDescription = null) },
                                 selectedContentColor = colorResource(id = R.color.green),
                                 unselectedContentColor = colorResource(id = R.color.nav_bg)
                             )
 }*/
