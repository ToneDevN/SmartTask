package com.example.myfirstapp.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfirstapp.DataClass.CreateSubtask
import com.example.myfirstapp.DataClass.DeleteSubtask
import com.example.myfirstapp.DataClass.ListCategory
import com.example.myfirstapp.DataClass.ListTask
import com.example.myfirstapp.DataClass.Subtask
import com.example.myfirstapp.DataClass.Task
import com.example.myfirstapp.DataClass.TaskByTemplateDate
import com.example.myfirstapp.DataClass.TaskXX
import com.example.myfirstapp.DataClass.TemplateIDRequest
import com.example.myfirstapp.DataClass.UpdateSubtask
import com.example.myfirstapp.DataClass.UpdateTemplate
import com.example.myfirstapp.DataClass.UpdateTodoListRequest
import com.example.myfirstapp.Screen
import com.example.myfirstapp.SharedPreferencesManager
import com.example.myfirstapp.TaskAPI
import com.example.myfirstapp.ui.theme.fontFamily
import com.example.myfirstapp.ui.theme.purple
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RestrictedApi", "UnrememberedMutableState")
@Composable
fun EditTemplateScreen(navController: NavController){
    val template = navController.previousBackStackEntry?.savedStateHandle?.get<TemplateIDRequest>("data")?:
    TemplateIDRequest(0)

    val createClient = TaskAPI.create()
    var subtaskCreate by remember { mutableStateOf(mutableListOf<String>()) }
    var subtaskTextCreate by remember { mutableStateOf("") }
    val contextForToast = LocalContext.current.applicationContext
    var sharedPreferences: SharedPreferencesManager = SharedPreferencesManager(context = contextForToast)
    val subtaskIdMap = remember { mutableStateMapOf<Int, Subtask>() }
    var completedSubtaskIds by remember { mutableStateOf(listOf<Int>()) }
    var showDialog by remember { mutableStateOf(false) }
    var templateState by  mutableStateOf<TaskXX?>(null)
    val key = remember { mutableStateOf(0) }
    var taskTitle by remember { mutableStateOf(templateState?.TodoList?.Title ?: "") }
    var notes by remember { mutableStateOf(templateState?.TodoList?.Description ?: "") }
    var url by remember { mutableStateOf(templateState?.TodoList?.URL ?: "") }
    var priority by remember { mutableStateOf(templateState?.TodoList?.Priority ?: 0) }
    var categoryID by remember { mutableStateOf<Int?>(templateState?.TodoList?.CategoryID ?: 0) }
    var subtasks by remember { mutableStateOf(mutableListOf("")) }
    var hour by remember { mutableStateOf("") }
    var minute by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(templateState?.TodoList?.Date ?: "") }
    var selectedTime by remember { mutableStateOf(templateState?.TodoList?.Time ?: "") }
    var selectedPriorityInt by remember { mutableStateOf(0) }
    var selectedPriority by remember { mutableStateOf("None") }
    val subtaskList = templateState?.TodoList?.Subtask ?: emptyList<Subtask>()
    val subtaskTexts by remember { mutableStateOf(subtaskList.toMutableList()) }



    try {
            val TemplateID = TemplateIDRequest(template.TemplateID)
            createClient.getTemplate("Bearer ${sharedPreferences.token}",TemplateID)
                .enqueue(object : Callback<TaskByTemplateDate> {
                    override fun onResponse(call: Call<TaskByTemplateDate>, response: Response<TaskByTemplateDate>) {
                        if (response.isSuccessful) {
                            val listTask = response.body()?.task
                            templateState = listTask

                        } else {
                            showToast(contextForToast, "Failed to fetch tasks")
                        }
                    }
                    override fun onFailure(call: Call<TaskByTemplateDate>, t: Throwable) {
                        showToast(contextForToast, "Error: Fail 1 ${t.message}")
                    }
                })
        } catch (e: Exception) {
            showToast(contextForToast, "Error: Fail 2 ${e.message}")
        }



    BoxWithConstraints {
        val height = constraints.maxHeight
        val contentHeight = height * 0.8f
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            IconButton(
                                onClick = {

                                    val tasksToUpdate = mutableListOf<UpdateSubtask>()
                                    val tasksToCreate = mutableStateListOf<CreateSubtask>()

                                    for (index in subtaskCreate.indices.reversed()) {
                                        val subtaskText = subtaskCreate[index]

                                        if (subtaskText != "") {
                                            val createRequest = CreateSubtask(templateState?.TodoList?.TaskID ?: 0, subtaskText)
                                            tasksToCreate.add(createRequest)
                                        }
                                    }

                                    for ((subtaskId, subtask) in subtaskIdMap.entries) {
                                        if (subtask.completed) {
                                            completedSubtaskIds = completedSubtaskIds + subtaskId
                                        }
                                    }



// Call the API to update existing subtasks
                                    for (updateRequest in tasksToUpdate) {
                                        print(updateRequest)
                                        createClient.updateSubtask("Bearer ${sharedPreferences.token}", updateRequest)
                                            .enqueue(object : Callback<Task> {
                                                override fun onResponse(call: Call<Task>, response: Response<Task>) {
                                                    if (response.isSuccessful) {
//                                                        showToast(contextForToast, "Subtask updated successfully")
                                                    } else {
//                                                        showToast(contextForToast, "Failed to update subtask")
                                                    }
                                                }
                                                override fun onFailure(call: Call<Task>, t: Throwable) {
                                                    showToast(contextForToast, "Error: ${t.message}")
                                                }
                                            })
                                    }


                                    // Call the API to create new subtasks
                                    for (createRequest in tasksToCreate) {
                                        try {
                                            createClient.createSubtask("Bearer ${sharedPreferences.token}", createRequest)
                                                .enqueue(object : Callback<Task> {
                                                    override fun onResponse(call: Call<Task>, response: Response<Task>) {
                                                        if (response.isSuccessful) {
//                                                            showToast(contextForToast, "Subtask created successfully")
                                                        } else {
//                                                            showToast(contextForToast, "Failed to create subtask")
                                                        }
                                                    }

                                                    override fun onFailure(call: Call<Task>, t: Throwable) {
                                                        showToast(contextForToast, "Error: ${t.message}")
                                                    }
                                                })
                                        } catch (e: Exception) {
                                            showToast(contextForToast, "Error: ${e.message}")
                                        }

                                    }

                                    for (subtaskId in completedSubtaskIds) {
                                        val deleteRequest = DeleteSubtask(templateState?.TodoList?.TaskID ?: 0, subtaskId)
                                        createClient.completeSubtask("Bearer ${sharedPreferences.token}", deleteRequest)
                                            .enqueue(object : Callback<Task> {
                                                override fun onResponse(call: Call<Task>, response: Response<Task>) {
                                                    if (response.isSuccessful) {
                                                        showToast(contextForToast, "Subtask completed successfully")
                                                    } else {
                                                        showToast(contextForToast, "Failed to complete subtask")
                                                    }
                                                }

                                                override fun onFailure(call: Call<Task>, t: Throwable) {
                                                    showToast(contextForToast, "Error: ${t.message}")
                                                }
                                            })
                                    }



                                    val updateRequest = UpdateTemplate(
                                        TemplateID = templateState?.TemplateID ?: 0,
                                        Title = taskTitle,
                                        Description = notes, // Updated note text
                                        URL = url, // Updated URL text
                                        Location = "",
                                        Priority = priority,
                                        Date = selectedDate,
                                        CategoryID = categoryID,
                                        Time = selectedTime,
                                        Subtasks = subtaskCreate.takeIf { it.isNotEmpty() }
                                    )

                                    try {
                                        createClient.updateTemplate("Bearer ${sharedPreferences.token}", updateRequest)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }



                                    // Navigate back to the home screen
                                    navController.popBackStack()
                                    navController.navigate(Screen.Home.route)
                                },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                            Spacer(modifier = Modifier.width(45.dp))
                            Text(
                                text = "Task Detail",
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
                                    )
                                    .clickable { showDialog = true },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Copy",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily,
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


                    ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = taskTitle,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                        fontSize = 21.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(35.dp))
                        // Due Date

                        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        val outputFormat = DateTimeFormatter.ofPattern("dd MMM")
//                        val adjustedDate = LocalDate.parse(selectedDate, outputFormat).plusDays(1)
                        val dateString = templateState?.TodoList?.Date
                        val date = LocalDateTime.parse(dateString, inputFormat)
                        val formattedDate = date.format(outputFormat)
//                        val formattedDateForDatabase = adjustedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//                        Text(text = formattedDate)
                        DateContentUpdate(
                            initialDate = formattedDate,
                            onDateSelected = { date ->
                                selectedDate = date
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))

                        // Time
                        TimeContentUpdate(
                            initialHour = if (selectedTime.isNotEmpty()) selectedTime.substringBefore(":").toInt() else 0,
                            initialMinute = if (selectedTime.isNotEmpty()) selectedTime.substringAfter(":").toInt() else 0,
                            onTimeSelected = { hour, minute ->
                                val formattedTime = String.format("%02d:%02d", hour, minute)
                                selectedTime = formattedTime
                            }
                        )

                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    // Note section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
//                        .background(Color(0xFFF1ECFF))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Column {
                            // Inside TaskDetailScreen composable
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Note",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
//                            SquareIcon(
//                                icon = Icons.Default.Edit,
//                                contentDescription = "Edit note",
//                                onClick = { /* Handle edit note */ },
//                                backgroundColor = purple
//                            )
                            }

                            OutlinedTextField(
                                value = notes,
                                onValueChange = { notes = it },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = fontFamily
                                ),
//                            label = { Text("Enter note") },
                                maxLines = 4,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Transparent, // Remove border color when focused
                                    unfocusedBorderColor = Color.Transparent // Remove border color when not focused
                                ),
                                shape = RoundedCornerShape(3.dp) // Optional: add rounded corners
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    // URL section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
//                        .background(Color(0xFFF1ECFF))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Column {
                            // Inside TaskDetailScreen composable
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "URL",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
//                            SquareIcon(
//                                icon = Icons.Default.Edit,
//                                contentDescription = "Edit URL",
//                                onClick = { /* Handle edit URL */ },
//                                backgroundColor = purple
//                            )
                            }

                            OutlinedTextField(
                                value = url,
                                onValueChange = { url = it },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = fontFamily
                                ),
                                placeholder = {
                                    Text("Enter URL", style = TextStyle(color = Color.Gray)) // Placeholder text
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Transparent, // Remove border color when focused
                                    unfocusedBorderColor = Color.Transparent // Remove border color when not focused
                                ),
                                shape = RoundedCornerShape(3.dp) // Optional: add rounded corners
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    // All SubTasks
                    Text(
                        text = "All SubTasks",
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
//                    LaunchedEffect(subtaskTexts) {
//                        // Trigger a recomposition when subtaskTexts changes
//                        Log.d("SubtaskSize", "Subtask size: ${subtaskTexts.size}")
//                    }
                    var draggedIndex by remember { mutableStateOf(-1) }
                    var offsetY by remember { mutableStateOf(0f) }




                    if (template != null) {
//
                        subtaskTexts.forEachIndexed { index, subtask ->
                            var isChecked by remember { mutableStateOf<Boolean>(subtask?.completed ?: false) }
                            var subId by remember { mutableIntStateOf(subtask?.subtaskID ?: 0) }

                            var nextSubtaskId by remember { mutableStateOf(templateState?.TodoList?.Subtask?.size ?: 0) }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(bottom = 8.dp, top = 8.dp)
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(Color(0xFFF1ECFF))
                            ) {
                                Spacer(modifier = Modifier.width(10.dp))

                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(purple, shape = CircleShape)
                                        .padding(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                if (isChecked) purple else Color.White,
                                                shape = CircleShape
                                            )
                                    ) {
                                        Checkbox(
                                            checked = isChecked,
                                            onCheckedChange = { newCheckedState ->
                                                isChecked = newCheckedState
                                                // Update the completion status in the subtask list
                                                subtaskTexts[index]?.completed = newCheckedState
                                                // Update the completion status in the database

                                                val subtaskToUpdate = subtaskTexts[index]
                                                val updateRequest = DeleteSubtask(subtaskToUpdate?.taskID ?: 0, subtaskToUpdate?.subtaskID ?: 0)
                                                createClient.completeSubtask("Bearer ${sharedPreferences.token}", updateRequest)
                                                    .enqueue(object : Callback<Task> {
                                                        override fun onResponse(call: Call<Task>, response: Response<Task>) {
                                                            if (response.isSuccessful) {
                                                                showToast(contextForToast, "Subtask created successfully")

                                                            } else {
                                                                // Handle unsuccessful response
                                                            }
                                                        }
                                                        override fun onFailure(call: Call<Task>, t: Throwable) {
                                                            // Handle failure
                                                        }
                                                    })


                                            },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = Color.Transparent,
                                                uncheckedColor = Color.Transparent,
                                                checkmarkColor = Color.White
                                            ),
                                            modifier = Modifier.size(24.dp),
                                        )
                                    }
                                }

                                LaunchedEffect(isChecked) {
                                    // Trigger a recomposition when isChecked changes
                                    // This will update the UI immediately after the Checkbox state changes
//                                    isChecked = !isChecked
                                    isChecked = subtask?.completed ?: false
                                }

                                OutlinedTextField(
                                    value = subtaskTexts.getOrNull(index)?.titleSubTask ?: "",
                                    onValueChange = { newValue ->
                                        val updatedSubtaskTexts = subtaskTexts.toMutableList()
                                        if (index < updatedSubtaskTexts.size) {
                                            updatedSubtaskTexts[index] = subtaskTexts.getOrNull(index)?.copy(titleSubTask = newValue) ?: return@OutlinedTextField
                                        } else {
                                            updatedSubtaskTexts.add(Subtask(subtaskID = nextSubtaskId++, titleSubTask = newValue, completed = false, taskID = templateState?.TodoList?.TaskID ?: 0))
                                        }
                                        subtaskTexts = updatedSubtaskTexts
                                    },
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .fillMaxWidth(),
                                    textStyle = TextStyle(
                                        fontFamily = fontFamily,
                                        fontSize = 14.sp
                                    ),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent
                                    ),
                                )


//                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(
                                    onClick = {
                                        subtaskTexts.removeAt(index)
                                    },
                                    modifier = Modifier
                                        .size(29.dp)
                                        .background(Color.Red, shape = CircleShape)
                                        .padding(5.dp)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete subtask", tint = Color.White)
                                }

                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }

                        for (i in 0..key.value) {
                            if (i >= 1) {
                                createSubtask(
                                    initialValue = subtaskTextCreate,
                                    onValueChange = { newText ->
                                        subtaskTextCreate = newText
                                    }
                                )


                            }
                        }




                        Spacer(modifier = Modifier.height(20.dp))
                    }

                }
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 32.dp, vertical = 20.dp)
                        .background(Color.White), // Adjust vertical padding here
                    horizontalArrangement =Arrangement.SpaceBetween,

                    ) {

                    Spacer(modifier=Modifier.width(10.dp))
                    Button(
                        onClick = {
                            key.value += 1

                            subtaskCreate.add(subtaskTextCreate.trim())
                            subtaskTextCreate = "" // Reset the text field
                            println(subtaskCreate)

                        },
                        modifier = Modifier
                            .weight(0.8f)
                            .padding(end = 8.dp)
                            .border(3.dp, purple, RoundedCornerShape(3.dp))
                            .background(purple, RoundedCornerShape(3.dp)),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = purple)
                    ) {
                        Text(
                            text = "Add Subtask",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Center
                        )
                    }

                    IconButton(
                        onClick = {
                            deleteTemplate(contextForToast,sharedPreferences,templateState?.TemplateID ?: 0)
                            showToast(contextForToast,"Delete ${templateState?.TemplateID}")
                        },
                        modifier = Modifier
                            .weight(0.2f)
                            .size(40.dp)
                            .padding(vertical = 10.dp, horizontal = 10.dp)
                            .border(3.dp, Color.Red, RoundedCornerShape(3.dp))
                            .background(Color.Red, RoundedCornerShape(3.dp))
                            .align(Alignment.CenterVertically), // Align vertically to CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }
                }
            }
        )
    }
}

fun deleteTemplate(context: Context, sharedPreferences: SharedPreferencesManager, TemplateID: Int) {
    val createClient = TaskAPI.create()

    try {
        createClient.deleteTemplate("Bearer ${sharedPreferences.token}", TemplateIDRequest(TemplateID))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}