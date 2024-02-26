package com.example.myfirstapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String,val name: String, val icon: ImageVector) {
    object Home: Screen(route = "home_screen", name = "Home", icon = Icons.Default.Home)
    object Personalinfo: Screen(route = "personalinfo_screen", name = "Personalinfo", icon = Icons.Default.Person)
}