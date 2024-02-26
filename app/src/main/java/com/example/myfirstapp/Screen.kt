package com.example.myfirstapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String,val name: String, val icon: ImageVector) {
    object Home: Screen(route = "home_screen", name = "Home", icon = Icons.Default.Home)
    object Personalinfo: Screen(route = "personalinfo_screen", name = "Personalinfo", icon = Icons.Default.Person)
    object AddTask: Screen(route = "addtask_screen", name= "AddTask", icon = Icons.Default.Add)
    object Routine: Screen(route = "Routine_screen", name= "Routine", icon = Icons.Default.Star)
    object TaskDetail: Screen(route = "taskdetail_screen", name= "TaskDetail", icon = Icons.Default.Build)
    object Setting: Screen(route = "Setting_Screen", name ="Setting", icon = Icons.Default.Settings);

    object  Scaffold: Screen(route="Scaffold_screen" ,name= "Scaffold", icon = Icons.Default.Close)

}