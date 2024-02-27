package com.example.myfirstapp.screens.Scaffold

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.myfirstapp.Screen

@SuppressLint("RestrictedApi")
@Composable
fun BottomBar(navController: NavController, contextForToast: Context) {
    val navigationItems = listOf(
        Screen.Home,
        Screen.Personalinfo
    )
    var selectedScreen by remember { mutableIntStateOf(0) }
    NavigationBar {
        navigationItems.forEachIndexed {index, screen ->
            NavigationBarItem(
                selected = (selectedScreen == 2),
                icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                onClick = {
                          if (navController.currentBackStack.value.size >= 2) {
                              navController.popBackStack()
                          }
                        selectedScreen = index
                        navController.navigate(screen.route)
                        Toast.makeText(contextForToast, screen.name, Toast.LENGTH_SHORT)
                }
            )
        }
    }

}