package com.example.myfirstapp.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.myfirstapp.DataClass.Task
import com.example.myfirstapp.DataClass.TodoListRequest
import com.example.myfirstapp.R
import com.example.myfirstapp.SharedPreferencesManager
import com.example.myfirstapp.TaskAPI
import com.example.myfirstapp.ui.theme.MyFirstAppTheme
import com.example.myfirstapp.ui.theme.fontFamily
import com.example.myfirstapp.ui.theme.purple
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Date


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyFirstAppTheme {

    }
}
@Composable
fun TaskTitleNotesURLContent(
    taskTitle: String,
    onTaskTitleChange: (String) -> Unit,
    notes: String,
    onNotesChange: (String) -> Unit,
    url: String,
    onUrlChange: (String) -> Unit
) {
    val cornerShape = RoundedCornerShape(3.dp)
    val borderWidth = 0.1.dp

    Column {
        Text(
            text = "Task Title",
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )


        OutlinedTextField(
            value = taskTitle,
            onValueChange = onTaskTitleChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(borderWidth, color = Color.Gray, shape = cornerShape),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            ),
            shape = cornerShape,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )


        Text(
            text = "Notes",
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(borderWidth, color = Color.Gray, shape = cornerShape),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            ),
            shape = cornerShape,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Text(
            text = "URL",
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        OutlinedTextField(
            value = url,
            onValueChange = onUrlChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(borderWidth, color = Color.Gray, shape = cornerShape),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            ),
            shape = cornerShape,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
    }
}
//navController: NavHostController
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddtaskScreen(navController: NavController) {
    var taskTitle by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("None") }
    var category by remember { mutableStateOf("None") }
    var subtasks by remember { mutableStateOf(mutableListOf("")) }

    var showDialog by remember { mutableStateOf(false) }
    val contextForToast = LocalContext.current
    var hour by remember { mutableStateOf("") }
    var minute by remember { mutableStateOf("") }
    val currentTime = LocalTime.now()
    var selectedHour by remember { mutableStateOf(currentTime.hour) }
    var selectedMinute by remember { mutableStateOf(currentTime.minute) }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    val formattedDate = SimpleDateFormat("dd MMM yyyy").format(Date(selectedDate))
    var selectedPriorityInt by remember { mutableStateOf(0) }

    val createClient = TaskAPI.create()
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)

    BoxWithConstraints {
        val height = constraints.maxHeight
        val contentHeight = height * 0.8f
        Scaffold(
            modifier = Modifier.padding(top = 30.dp).background(Color.White),
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
                                    navController.popBackStack()
                                },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                            Text(
                                text = "New Task",
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(50.dp))
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

                    TaskTitleNotesURLContent(
                        taskTitle = taskTitle,
                        onTaskTitleChange = { taskTitle = it },
                        notes = notes,
                        onNotesChange = { notes = it },
                        url = url,
                        onUrlChange = { url = it }
                    )

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

                    PrioritySelector(
                        modifier = Modifier.fillMaxWidth(),
                        priority = selectedPriorityInt,
                        onPrioritySelected = { selectedPriorityInt = it }
                    )

                    CategoryDropdown(
                        modifier = Modifier.fillMaxWidth(),
                        category = category,
                        onCategorySelected = { category = it }
                    )

                    Subtasks(
                        modifier = Modifier.fillMaxWidth(),
                        subtasks = subtasks,
                        onSubtasksChanged = { subtasks = it.toMutableList() }
                    )
                }
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 32.dp, vertical = 20.dp)
                        .background(Color.White),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
//                            val request = TodoListRequest(taskTitle, notes, url, priority, null, subtasks)
                            val request = TodoListRequest(taskTitle, notes, url,"" , selectedPriorityInt, formattedDate, "$hour:$minute", subtasks)

                            createClient.createTask("Bearer ${sharedPreferences.token}",request)
                                .enqueue(object : Callback<Task> {
                                    override fun onResponse(call: Call<Task>, response: Response<Task>) {
                                        if (response.isSuccessful) {
                                            showToast(contextForToast, "Task created successfully")
                                            navController.popBackStack()
                                        } else {
                                            showToast(contextForToast, "Failed to create task")
                                        }
                                    }

                                    override fun onFailure(call: Call<Task>, t: Throwable) {
                                        showToast(contextForToast, "Error: ${t.message}")
                                    }
                                })
                        },
                        modifier = Modifier
                            .weight(0.8f)
                            .padding(end = 8.dp)
                            .border(3.dp, purple, RoundedCornerShape(3.dp))
                            .background(purple, RoundedCornerShape(3.dp)),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = purple)
                    )
                     {
                        Text("Done", color = Color.White)
                    }
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeContent(): Pair<Int, Int> {
    val currentTime = LocalTime.now()
    var selectedHour by remember { mutableStateOf(currentTime.hour) }
    var selectedMinute by remember { mutableStateOf(currentTime.minute) }


    var showDialog by remember { mutableStateOf(false) }
    val cornerShape = RoundedCornerShape(3.dp)
    val cornerShapeDialog = RoundedCornerShape(10.dp)
    var timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute
    )

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
                            selectedHour = timePickerState.hour
                            selectedMinute = timePickerState.minute
                        }) {
                            Text(text = "Done", color = purple)
                        }
                    }
                }
            }
        }
    }

    // Display the "Select Time" text and the selected time in a row with an icon button to open the time picker dialog
    Column(
        modifier = Modifier.padding(start = 0.dp, top = 4.dp),
        horizontalAlignment = Alignment.Start
    )  {
        // Styled "Select Time" text
        Text(
            text = "Select Time",
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )

        // Outlined text field to display the selected time along with the icon
        Box(
            modifier = Modifier.fillMaxWidth(),
        )  {
            OutlinedTextField(
                value = String.format("%02d:%02d", selectedHour, selectedMinute),
                onValueChange = { },
                modifier = Modifier
                    .width(210.dp)
                    .height(50.dp)
                    .border(1.dp, Color.Gray, shape = cornerShape),
                shape = cornerShape,
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                enabled = false, // Disable editing
                trailingIcon = {
                    // Custom icon button to open the time picker dialog
                    IconButton(
                        onClick = { showDialog = true },
                    ) {
                        // Use the custom drawable resource as the icon
                        Image(
                            painter = painterResource(id = R.drawable.icons8_clock_30),
                            contentDescription = "Time Picker Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    }

    // Return the selected hour and minute as a Pair
    return selectedHour to selectedMinute
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContent(selectedDate: Long, onDateSelected: (Long) -> Unit) {


    val calendar = Calendar.getInstance()
    calendar.timeInMillis = selectedDate
    val cornerShape = RoundedCornerShape(3.dp)

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
    )
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(start = 0.dp, top = 4.dp)
//        horizontalAlignment = Alignment.Start
    ) {
        // Styled "Select Date" text
        Text(
            text = "Select Date",
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        // Outlined text field to display the selected date along with the icon
        Box(
            modifier = Modifier.fillMaxWidth(0.5f),
            contentAlignment = Alignment.CenterStart
        ) {
            OutlinedTextField(
                value = SimpleDateFormat("dd MMM yyyy").format(calendar.time),
                onValueChange = {}, // No-op, since this is for display only
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .border(1.dp, Color.Gray, shape = cornerShape),
                shape = cornerShape,
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                enabled = false, // Disable editing
                trailingIcon = {
                    // Custom icon button to open the time picker dialog
                    IconButton(
                        onClick = { showDatePicker = true },
                    ) {
                        // Use the custom drawable resource as the icon
                        Image(
                            painter = painterResource(id = R.drawable.icons8_calendar_30),
                            contentDescription = "Time Picker Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }

        // Call the function for the date picker dialog
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        onDateSelected(datePickerState.selectedDateMillis ?: selectedDate)
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
fun PrioritySelector(
    modifier: Modifier = Modifier,
    priority: Int,
    onPrioritySelected: (Int) -> Unit
): Int {
    val priorities = listOf("None", "Low", "Medium", "High")
    var selectedPriority by remember { mutableStateOf(priority) }
    var expanded by remember { mutableStateOf(false) }

    // Create a black outline
    val outlineBorder = BorderStroke(1.dp, Color.Gray)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, top = 4.dp),
    ) {

        Text(
            text = "Priority",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        // Create a surface with an outline
        Surface(
            shape = RoundedCornerShape(3.dp),
            border = outlineBorder,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Display the selected priority
                OutlinedTextField(
                    value = priorities[selectedPriority],
                    onValueChange = { },
                    enabled = false,
                    modifier = Modifier
                        .weight(1f)
                        .then(Modifier.fillMaxWidth()),
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            expanded = !expanded
                        }
                    ),
                    trailingIcon = {
                        // Icon button for the dropdown
                        IconButton(
                            onClick = {
                                expanded = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Priority Dropdown",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

            // Display the dropdown menu when the dropdown icon is clicked
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                priorities.forEachIndexed { index, priority ->
                    DropdownMenuItem(
                        text = { Text(priority) },
                        onClick = {
                            selectedPriority = index
                            onPrioritySelected(index)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    return selectedPriority
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    modifier: Modifier = Modifier,
    category: String,
    onCategorySelected: (String) -> Unit
): String {
    val categories = listOf("None","Personal", "Work", "Study", "Shopping")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var expanded by remember { mutableStateOf(false) }

    // Create a black outline
    val outlineBorder = BorderStroke(1.dp, Color.Gray)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, top = 4.dp),
    ) {

        Text(
            text = "Category",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )

        // Create a surface with an outline
        Surface(
            shape = RoundedCornerShape(3.dp),
            border = outlineBorder,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Display the selected category
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = { },
                    enabled = false,
                    modifier = Modifier
                        .weight(1f)
                        .then(Modifier.fillMaxWidth()),
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            expanded = !expanded
                        }
                    ),
                    trailingIcon = {
                        // Icon button for the dropdown
                        IconButton(
                            onClick = {
                                expanded = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Category Dropdown",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }

            // Display the dropdown menu when the dropdown icon is clicked
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(text ={ Text(
                        category
                    )},
                        onClick = {
                            selectedCategory = category
                            onCategorySelected(category)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    return selectedCategory
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Subtasks(
    modifier: Modifier = Modifier,
    subtasks: MutableList<String>,
    onSubtasksChanged: (List<String>) -> Unit
) {
    var subtasksState by remember { mutableStateOf(subtasks.toList()) }

    Column(
        modifier = modifier.padding(start = 0.dp, top = 4.dp)
    ) {
        Text(
            text = "Subtasks",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )

        subtasksState.forEachIndexed { index, subtask ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = subtask,
                    onValueChange = { updatedSubtask ->
                        subtasksState = subtasksState.toMutableList().apply { set(index, updatedSubtask) }
                    },
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 8.dp)
                        .height(50.dp)
                        .weight(1f),
                    textStyle = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    ),
                    shape = RoundedCornerShape(3.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )

                if (index == subtasksState.size - 1) {
                    IconButton(
                        onClick = {
                            subtasksState = subtasksState.toMutableList().also { it.add("") }
                        },
                        modifier = Modifier
                            .background(purple, RoundedCornerShape(3.dp))
                            .padding(vertical = 1.dp, horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Subtask",
                            tint = Color.White
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            subtasksState = subtasksState.toMutableList().apply { removeAt(index) }
                        },
                        modifier = Modifier
                            .padding(vertical = 1.dp, horizontal = 8.dp)
                            .width(IntrinsicSize.Max) // Set the width to match the maximum intrinsic width
                            .background(Color.Red, RoundedCornerShape(3.dp)),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Subtask",
                            tint = Color.White
                        )
                    }



                }
            }
        }
    }

    // Trigger recomposition when subtasks list changes
    LaunchedEffect(subtasksState) {
        onSubtasksChanged(subtasksState)
    }
}


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}





