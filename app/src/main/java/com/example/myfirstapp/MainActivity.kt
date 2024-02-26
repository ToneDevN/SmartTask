package com.example.myfirstapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfirstapp.screens.SettingsScreen
import com.example.myfirstapp.ui.theme.MyFirstAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    ExampleScreen()
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()
        ModalNavigationDrawer(drawerContent = { drawerContents(drawerState) }, drawerState = drawerState) {
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
                )},

                bottomBar = { BottomBar(navController, contextForToast) },
            ) { innerPadding  ->
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyFirstAppTheme {
        Greeting("Android")
    }
}

@Composable
fun ExampleScreen() {
    // Sample data
    val title = "Wash the Dish"
    val dueDate = "20 May 2024"
    val time = "9:45"
    val note = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
    val url = "https://www.example.com"
    val subtasks = listOf("Buy the power", "Take the laundry", "Sleep")

    val isUserSignedIn = true
    val userName = "Jetsada"

//    AddtaskScreen()
//    TaskDetailScreen(
//        onBackClicked = { /* TODO: Navigate back */ },
//        onCopyClicked = { /* TODO: Implement copy functionality */ },
//        title = title,
//        dueDate = dueDate,
//        time = time,
//        note = note,
//        url = url,
//        subtasks = subtasks
//    )
//    onBackClicked: () -> Unit,
//    isUserSignedIn: Boolean,
//    userName: String?,
//    onSignOutClicked: () -> Unit
    SettingsScreen(
        onBackClicked = { /*TODO*/ },
        isUserSignedIn = isUserSignedIn,
        userName = userName) {

    }
}