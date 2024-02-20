package com.example.myfirstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfirstapp.screens.SettingsScreen
import com.example.myfirstapp.ui.theme.MyFirstAppTheme

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