package com.example.myfirstapp.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.myfirstapp.R
import com.example.myfirstapp.Screen
import com.example.myfirstapp.ui.theme.fontFamily
import com.example.myfirstapp.ui.theme.purple
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.math.roundToInt

fun addSubtask(subtasks: MutableList<String>) {
    subtasks.add("")
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TaskDetailScreen(navController: NavHostController) {

    val dueDate = "20 May 2024"
    // Define state variables to hold the selected date and time
    var selectedDate by remember { mutableStateOf("20 May 2024") }

    var noteText by remember { mutableStateOf("Dont remember what thing it have to do") }
    var urlText by remember { mutableStateOf("www.Example.com") }
    var title by remember { mutableStateOf("Mobile App Wireframe")}

    var subtasks = listOf("Buy the power", "Take the laundry", "Sleep")
    var time = "9:45"
    var subtaskTexts by remember { mutableStateOf(subtasks.toMutableStateList()) }
    var showDialog by remember { mutableStateOf(false)}

    var selectedTime by remember { mutableStateOf(time) }
    var hour by remember { mutableStateOf("") }
    var minute by remember {
        mutableStateOf("")
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
                                onClick = { navController.navigate(Screen.Home.route) },
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
                                    .clickable { showDialog=true },
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
                        text = title,
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
                        DateContentUpdate(
                            initialDate = dueDate,
                            onDateSelected = { date ->
                                selectedDate = date
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))

                        // Time
                        TimeContentUpdate(
                            initialHour = selectedTime.substringBefore(":").toInt(),
                            initialMinute = selectedTime.substringAfter(":").toInt(),
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
                                value = noteText,
                                onValueChange = { noteText = it },
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
                                value = urlText,
                                onValueChange = { urlText = it },
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

                    var draggedIndex by remember { mutableStateOf(-1) }
                    var offsetY by remember { mutableStateOf(0f) }

                    subtaskTexts.forEachIndexed { index, subtask ->
                        var isChecked by remember { mutableStateOf(false) }
                        var draggedOffsetY by remember { mutableStateOf(0f) }

                        val offsetYAnimation = remember { Animatable(0f) }
                        LaunchedEffect(draggedIndex) {
                            if (index == draggedIndex) {
                                offsetYAnimation.animateTo(draggedOffsetY)
                            } else {
                                offsetYAnimation.animateTo(0f)
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(bottom = 8.dp, top = 8.dp)
                                .fillMaxWidth()
                                .height(50.dp)
                                .offset { IntOffset(0, offsetYAnimation.value.roundToInt()) }
                                .background(
                                    Color(0xFFF1ECFF),
                                    shape = RoundedCornerShape(3.dp)
                                )
//                                .draggable1(
//                                    state = rememberDraggableState { delta ->
//                                        if (draggedIndex == -1) {
//                                            offsetY += delta
//                                            val newIndex = (index + (delta / 50)).coerceIn(
//                                                0f,
//                                                (subtaskTexts.size - 1).toFloat()
//                                            )
//                                            if (index.toFloat() != newIndex) {
//                                                draggedIndex = index
//                                                draggedOffsetY = offsetY
//                                            }
//                                        } else {
//                                            val newIndex = (index + (delta / 50)).coerceIn(
//                                                0f,
//                                                (subtaskTexts.size - 1).toFloat()
//                                            )
//                                            if (index.toFloat() != newIndex) {
//                                                // Update the subtaskTexts list to reflect the new order
//                                                val draggedSubtask = subtaskTexts[draggedIndex]
//                                                subtaskTexts.removeAt(draggedIndex)
//                                                subtaskTexts.add(newIndex.toInt(), draggedSubtask)
//                                                draggedIndex = newIndex.toInt()
//                                            }
//                                        }
//                                    },
//                                    orientation = Orientation.Vertical,
//                                    onDragStopped = {
//                                        if (draggedIndex != -1) {
//                                            draggedIndex = -1
//                                            offsetY = 0f
//                                        }
//                                    }
//                                ),
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
                                        .background(if (isChecked) purple else Color.White, shape = CircleShape)
                                ) {
                                    Checkbox(
                                        checked = isChecked,
                                        onCheckedChange = { isChecked = it },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color.Transparent,
                                            uncheckedColor = Color.Transparent,
                                            checkmarkColor = Color.White
                                        ),
                                        modifier = Modifier.size(24.dp),
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                OutlinedTextField(
                                    value = subtaskTexts[index],
                                    onValueChange = { subtaskTexts[index] = it },
                                    modifier = Modifier.padding(start = 8.dp).fillMaxWidth(),
                                    textStyle = TextStyle(fontFamily = fontFamily, fontSize = 14.sp),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent
                                    ),
                                )
                            }

                            IconButton(
                                onClick = { subtaskTexts.removeAt(index) },
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
                    Spacer(modifier = Modifier.height(20.dp))
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = "Values") },
                        text = {
                            Column {
                                Text("Task Title: $title")
                                Text("Notes: $noteText")
                                Text("URL: $urlText")
//                                Text("Priority: ${priority}")
//                                Text("Category: ${category}")
                                Text(
                                    text = "Subtasks: ${subtasks.mapIndexed { index, subtask -> "$subtask(${index + 1})" }.joinToString(", ")}"
                                )
                                Text("Due Date: ${selectedDate}")
                                Text("Time: ${selectedTime}")
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = { showDialog = false },
//                                colors = ButtonDefaults.buttonColors(backgroundColor = purple)
                            ) {
                                Text("Close", color = Color.White)
                            }
                        }
                    )
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
                        onClick = { addSubtask(subtaskTexts) },
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
                        onClick = { /* Handle Remove button click */ },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContentUpdate(
    initialDate: String,
    onDateSelected: (String) -> Unit
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    var showDatePicker by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis
    )

    Column(
        modifier = Modifier.padding(start = 0.dp, top = 4.dp)
    ) {
        // Styled "Due Date" text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { showDatePicker = true }
        ) {
            // Calendar icon with background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = purple, shape = RoundedCornerShape(3.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_calendar_50),
                    contentDescription = "Time Picker Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(0.4f)
            ) {
                Text(
                    text = "Due Date",
                    color = Color(0xFF8CAAB9),
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Selected date text
                    Text(
                        text = selectedDate,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }


        // Call the function for the date picker dialog
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        selectedDate = SimpleDateFormat("dd MMM yyyy").format(Date(datePickerState.selectedDateMillis ?: calendar.timeInMillis))
                        onDateSelected(selectedDate)
                    }) {
                        Text(text = "Done", color = purple)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text(text = "Cancel", color = Color.Gray)
                    }
                }) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeContentUpdate(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (Int, Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val cornerShapeDialog = RoundedCornerShape(10.dp)
    var timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute
    )

    Column(
        modifier = Modifier.padding(start = 0.dp, top = 4.dp)
    ) {
        // Styled "Select Time" text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { showDialog = true }
        ) {
            // Clock icon with background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = purple, shape = RoundedCornerShape(3.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_clock_50),
                    contentDescription = "Edit Time",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Select Time",
                    color = Color(0xFF8CAAB9),
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Selected time text
                    Text(
                        text = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                }
            }
        }

        // Show the time picker dialog when showDialog is true
        if (showDialog) {
            AlertDialog(
                modifier = Modifier.fillMaxWidth(0.8f),
                onDismissRequest = { showDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface(
                    color = Color.White, // Set the background color for the AlertDialog
                    modifier = Modifier.fillMaxWidth(),
                    shape = cornerShapeDialog // Adjust the corner radius as needed
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TimePicker(state = timePickerState)
                        Row(
                            modifier = Modifier.padding(top = 6.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(onClick = { showDialog = false }) {
                                Text("Dismiss", color = Color.Gray)
                            }
                            TextButton(onClick = {
                                showDialog = false
                                // Update selectedHour and selectedMinute when Confirm is clicked
                                onTimeSelected(timePickerState.hour, timePickerState.minute)
                            }) {
                                Text(text = "Done", color = Color.Magenta)
                            }
                        }
                    }
                }
            }
        }
    }
}









