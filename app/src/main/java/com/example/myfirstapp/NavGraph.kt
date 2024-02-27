package com.example.myfirstapp

import CreateAccountScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myfirstapp.screens.AddRoutineScreen
import com.example.myfirstapp.screens.AddtaskScreen
import com.example.myfirstapp.screens.EditTemplateScreen
import com.example.myfirstapp.screens.FirstScreen
import com.example.myfirstapp.screens.HomeScreen
import com.example.myfirstapp.screens.Personalinfo
import com.example.myfirstapp.screens.Scaffold.MyScaffold
import com.example.myfirstapp.screens.SettingsScreen
import com.example.myfirstapp.screens.SignInScreen
import com.example.myfirstapp.screens.TaskDetailScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            MyScaffold(navController = navController) {
                HomeScreen(navController)
            }
        }
        composable(Screen.Personalinfo.route) {
            MyScaffold(navController = navController) {
                Personalinfo(navController)
            }
        }
        composable(Screen.AddTask.route) {
            AddtaskScreen(navController)
        }
        composable(Screen.Create.route) {
            CreateAccountScreen(navController)
        }
        composable(Screen.TaskDetail.route){
            TaskDetailScreen(navController)
        }
        composable(Screen.Setting.route){
            SettingsScreen(navController)
        }
        composable(Screen.Routine.route){
            AddRoutineScreen(navController)
        }
        composable(Screen.First.route){
            FirstScreen(navController)
        }
        composable(Screen.SignIn.route){
            SignInScreen(navController)
        }
        composable(Screen.EditTemplate.route){
            EditTemplateScreen(navController)
        }

//        composable(Screen.Scaffold.route){
//             MyScaffold(navController)
//}

    }
}


