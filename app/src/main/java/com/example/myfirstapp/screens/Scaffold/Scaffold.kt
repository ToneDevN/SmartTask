package com.example.myfirstapp.screens.Scaffold

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.Screen
import kotlinx.coroutines.launch


@Composable
fun MyFloatingActionButton(navController: NavController,task:Int){
    FloatingActionButton(onClick = {
        if(task == 1){
            navController.navigate(Screen.AddTask.route)}
        else{
            navController.navigate(Screen.Routine.route)
        }
    })
    {
        if(task == 1) {
            Row {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add task")
                Text(text = "New Task")
            }
        }else{
            Row {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add task")
                Text(text = "New Routine")
            }
        }

    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(navController: NavController, content: @Composable () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()
        ModalNavigationDrawer(drawerContent = { drawerContents(drawerState,navController) }, drawerState = drawerState) {
            val contextForToast = LocalContext.current.applicationContext
            val navController = rememberNavController()
            Scaffold (
                topBar = {
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
                },

                bottomBar = { BottomBar(navController, contextForToast) },
                floatingActionButton = {
                    Column {
                        MyFloatingActionButton(navController, task = 1);
Spacer(modifier = Modifier.height(10.dp))
                        MyFloatingActionButton(navController, task = 2)
                    }
                   },
                floatingActionButtonPosition = FabPosition.End

            ) { innerPadding  ->
                Column(modifier= Modifier
                    .padding(innerPadding)
                    .background(Color.White)) {
                    content()
                }

            }
        }
    }
}
