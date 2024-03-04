package com.example.myfirstapp.screens.Scaffold

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfirstapp.DataClass.Category
import com.example.myfirstapp.DataClass.ListCategory
import com.example.myfirstapp.DataClass.ListTemplate
import com.example.myfirstapp.DataClass.LoginClass
import com.example.myfirstapp.DataClass.TemplateData
import com.example.myfirstapp.DataClass.User
import com.example.myfirstapp.DataClass.UserClass
import com.example.myfirstapp.Screen
import com.example.myfirstapp.SharedPreferencesManager
import com.example.myfirstapp.TaskAPI
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


data class NavigationDrawerData(val label: String, val icon: ImageVector)

private fun prepareNavigationDrawerItems(): List<NavigationDrawerData> {
    val drawerItemList = arrayListOf<NavigationDrawerData>()
    drawerItemList.add(NavigationDrawerData(label = "Category/Tag", icon = Icons.Filled.Info))
    drawerItemList.add(NavigationDrawerData(label = "Template", icon = Icons.Default.List))
    return drawerItemList
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawerContents(drawerState:DrawerState,navController:NavController){
    val contextForToast = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()
    val drawerItemList = prepareNavigationDrawerItems()
    var isExpanded by remember { mutableStateOf(List(drawerItemList.size) { false }) }
    var Arrowexpanded by remember { mutableStateOf(List(drawerItemList.size) { false }) }

    var categoryState by mutableStateOf<ListCategory?>(null)
    var templateState by mutableStateOf<TemplateData?>(null)
    val selectedItem = remember { mutableStateOf(null) }
    val selectedSetting by remember { mutableStateOf("Setting") }
    val createClient = TaskAPI.create()
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)

    // Category
    createClient.getCategory("Bearer ${sharedPreferences.token}")
        .enqueue(object: Callback<ListCategory?> {
            @SuppressLint("RestrictedApi")
            override fun onResponse(call: Call<ListCategory?>, response: Response<ListCategory?>) {
                if(response.isSuccessful){
                    val listCategory = response.body()
                    if(listCategory != null){
                        categoryState = listCategory
                    } else {
                        // Token is empty or null, indicating login failure
                        Toast.makeText(contextForToast, "Null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Response not successful (e.g., HTTP error)
                    Toast.makeText(contextForToast, "Category not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ListCategory?>, t: Throwable) {
                // Network request failed
                Toast.makeText(contextForToast, "Error onFailure", Toast.LENGTH_SHORT).show()
            }
        })

    // Template
    createClient.getListTemplate("Bearer ${sharedPreferences.token}")
        .enqueue(object: Callback<TemplateData> {
            @SuppressLint("RestrictedApi")
            override fun onResponse(call: Call<TemplateData>, response: Response<TemplateData>) {
                if(response.isSuccessful){
                    val listTemplate = response.body()
                    if(listTemplate != null){
                        // การร้องขอข้อมูลสำเร็จ
                        templateState = listTemplate
                    } else {
                        // ข้อมูลที่ได้รับมาไม่ถูกต้อง
                        Toast.makeText(contextForToast, "Null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // การร้องขอข้อมูลไม่สำเร็จ (เช่น มีข้อผิดพลาด HTTP)
                    Toast.makeText(contextForToast, "Template request Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TemplateData>, t: Throwable) {
                // การร้องขอข้อมูลผิดพลาด
                Toast.makeText(contextForToast, "Error onFailure", Toast.LENGTH_SHORT).show()
            }
        })



    // Fetch user data
    var user by remember { mutableStateOf<User?>(null) }
    LaunchedEffect(Unit) {
        try {
            createClient.getUser("Bearer ${sharedPreferences.token}")
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            user = response.body()
                        } else {
                            Toast.makeText(contextForToast, "Null", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(contextForToast, "User not found", Toast.LENGTH_SHORT).show()
                    }
                })
        } catch (e: Exception) {
            // Handle exception
            Toast.makeText(contextForToast, "Error onFailure", Toast.LENGTH_SHORT).show()
        }
    }
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
                                    if(isExpanded[0]){
                                    categoryState!!.categories.forEachIndexed { index, item->
                                        NavigationDrawerItem(
                                            label = {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(text = item.category)

                                                } },
                                            selected = (item == selectedItem.value),
                                            onClick = {

                                            },
                                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                        )
                                    }

                                    NavigationDrawerItem(
                                        label = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(text = "New")
                                                Icon(
                                                    imageVector = Icons.Default.Add,
                                                    contentDescription = "Expand"
                                                )
                                            } },
                                        selected = (
                                                item == selectedItem.value
                                                ),
                                        onClick = {
                                            isExpanded = isExpanded.toMutableList().also { it[index] = !it[index] }
                                            Arrowexpanded = Arrowexpanded.toMutableList().also { it[index] = !it[index] }
                                        },
                                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )}
                                    else if (isExpanded[1]){
//                                        templateState!!.TemplateData.forEachIndexed { index, item->
//                                            NavigationDrawerItem(
//                                                label = {
//                                                    Row(verticalAlignment = Alignment.CenterVertically) {
//                                                        Text(text = item.todoList.title)
//
//                                                    } },
//                                                selected = (item == selectedItem.value),
//                                                onClick = {
//
//                                                },
//                                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//                                            )
//                                        }

                                        NavigationDrawerItem(
                                            label = {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(text = "New")
                                                    Icon(
                                                        imageVector = Icons.Default.Add,
                                                        contentDescription = "Expand"
                                                    )
                                                } },
                                            selected = (
                                                    item == selectedItem.value
                                                    ),
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
                        onClick = {

                            navController.currentBackStackEntry?.savedStateHandle?.set("user",
                                User(
                                        user!!.firstName, user!!.lastName,user!!.profileImg
                                    )
                            )

                            navController.navigate(Screen.Setting.route)

                                  },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                }
            }
        }


