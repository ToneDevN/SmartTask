package com.example.myfirstapp.screens.Scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


data class NavigationDrawerData(val label: String, val icon: ImageVector)

private fun prepareNavigationDrawerItems(): List<NavigationDrawerData> {
    val drawerItemList = arrayListOf<NavigationDrawerData>()
    drawerItemList.add(NavigationDrawerData(label = "Category/Tag", icon = Icons.Filled.Info))
    drawerItemList.add(NavigationDrawerData(label = "Template", icon = Icons.Filled.List))
    return drawerItemList
}

@Composable
fun DropDown(){

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){

    val contextForToast = LocalContext.current.applicationContext
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val drawerItemList = prepareNavigationDrawerItems()
    var isExpanded by remember { mutableStateOf(List(drawerItemList.size) { false }) }
    var Arrowexpanded by remember { mutableStateOf(List(drawerItemList.size) { false }) }
    val selectedItem = remember { mutableStateOf(null) }
    var selectedSetting by remember { mutableStateOf("Setting") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier
                .requiredWidth(250.dp)
                .fillMaxHeight()) {
                Spacer(modifier = Modifier.height(12.dp))
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {coroutineScope.launch {
                            drawerState.close()
                        } }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = "Menu",
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f)
                        ) }
                    drawerItemList.forEachIndexed { index, item ->
                        Column {
                            NavigationDrawerItem(
                                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                                label = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = item.label)
                                        Icon(
                                            imageVector = if (Arrowexpanded[index]) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                            contentDescription = "Expand"
                                        )
                                } },
                                selected = (item == selectedItem.value),
                                onClick = {
                                    isExpanded = isExpanded.toMutableList().also { it[index] = !it[index] }
                                    Arrowexpanded = Arrowexpanded.toMutableList().also { it[index] = !it[index] }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                            AnimatedVisibility(visible = isExpanded[index]) {
                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 48.dp)) {
                                    NavigationDrawerItem(
                                        label = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(text = item.label)
                                                Icon(
                                                    imageVector = if (Arrowexpanded[index]) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                                    contentDescription = "Expand"
                                                )
                                            } },
                                        selected = (item == selectedItem.value),
                                        onClick = {
                                            isExpanded = isExpanded.toMutableList().also { it[index] = !it[index] }
                                            Arrowexpanded = Arrowexpanded.toMutableList().also { it[index] = !it[index] }
                                        },
                                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }

                            }
                        }

                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    Arrangement.Bottom
                ) {
                    Canvas(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)) {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, size.height / 2),
                            end = Offset(size.width, size.height / 2),
                            strokeWidth = 1.2.dp.toPx(),
                        )
                    }
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Setting"
                            )
                        },
                        selected = (selectedSetting == ""),
                        label = {
                            Text(text = "Setting")
                        },
                        onClick = {},
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                }}
        }
        ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(text = "Smart Task") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White),
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )

        }

    }




}