package com.example.myfirstapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfirstapp.DataClass.Task
import com.example.myfirstapp.DataClass.TemplateIDRequest
import com.example.myfirstapp.Screen
import com.example.myfirstapp.ui.theme.fontFamily
import com.example.myfirstapp.ui.theme.purple

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RestrictedApi")
@Composable
fun EditTemplateScreen(navController: NavController){
    val task = navController.previousBackStackEntry?.savedStateHandle?.get<TemplateIDRequest>("data")?:
    TemplateIDRequest(0)
    var taskTitle by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var subtasks by remember { mutableStateOf(mutableListOf("")) }
    var hour by remember { mutableStateOf("") }
    var minute by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var selectedTime by remember { mutableStateOf("") }
    var selectedPriorityInt by remember { mutableStateOf(0) }
    var selectedPriority by remember { mutableStateOf("None") }
    

    BoxWithConstraints {
        val height = constraints.maxHeight
        val contentHeight = height * 0.8f
        Scaffold(
            modifier = Modifier.padding(top = 30.dp),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    if (navController.currentBackStack.value.size >= 2) {
                                        navController.popBackStack()
                                    }
                                    navController.navigate(Screen.Home.route)
                                },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                            Spacer(modifier = Modifier.width(45.dp))
                            Text(
                                text = "Edit Template",
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(10.dp)) // Adjust spacing if needed
                            Box(
                                modifier = Modifier
                                    .height(35.dp)
                                    .width(80.dp)
                                    .padding(horizontal = 16.dp)
                                    .background(
                                        Color(0xFFF1ECFF),
                                        shape = RoundedCornerShape(3.dp)
                                    ),
                                contentAlignment = Alignment.Center,

                            ) {
                                Text(
                                    text = "Done",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6A0DAD), // Set text color to purple
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
            },

    content = {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .height(contentHeight.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(bottom = 60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(50.dp))

                    // Your existing content...
                    TaskTitleNotesURLContent(
                        taskTitle = taskTitle,
                        onTaskTitleChange = { taskTitle = it },
                        notes = notes,
                        onNotesChange = { notes = it },
                        url = url,
                        onUrlChange = { url = it }
                    )

                    // Select Date and Time
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DateContent(selectedDate = selectedDate, onDateSelected = { selectedDate = it })
                        var (hourValue, minuteValue) = TimeContent()
                        hour = if (hourValue < 10) "0${hourValue}" else "$hourValue"
                        minute = if (minuteValue < 10) "0${minuteValue}" else "$minuteValue"
                    }

                    // Priority
                    PrioritySelector(
                        modifier = Modifier.fillMaxWidth(),
                        priority = selectedPriorityInt,
                        onPrioritySelected = { selectedPriorityInt = it }
                    )

                    // Category Dropdown
                    CategoryDropdown(
                        modifier = Modifier.fillMaxWidth(),
                        category = category,
                        onCategorySelected = { /* Handle category selection */ }
                    )

                    // Subtasks
                    Subtasks(
                        modifier = Modifier.fillMaxWidth(),
                        subtasks = subtasks,
                        onSubtasksChanged = { /* Handle subtasks change */ }
                    )
                }
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().background(Color.White)
                        .padding(horizontal = 32.dp, vertical = 20.dp)
                        .background(Color.White),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(
                        onClick = { /* Handle New Template button click */ },
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(end = 8.dp)
                            .background(purple, RoundedCornerShape(3.dp)),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = purple)
                    ) {
                        Text(
                            text = "New Template",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = { /* Handle New Task button click */ },
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(start = 8.dp)
                            .background(purple, RoundedCornerShape(3.dp)),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = purple)
                    ) {
                        Text(
                            text = "New Task",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }


        )
    }
}