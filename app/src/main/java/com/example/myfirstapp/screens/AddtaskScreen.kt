package com.example.myfirstapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.myfirstapp.ui.theme.MyFirstAppTheme
import com.example.myfirstapp.ui.theme.fontFamily
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Date


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyFirstAppTheme {
        AddtaskScreen(

        )
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
            shape = cornerShape,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
    }
}

//navController: NavHostController navController.popBackStack()
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddtaskScreen() {
    var taskTitle by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var subtasks by remember { mutableStateOf(mutableListOf("")) }
    var hour by remember {
        mutableStateOf("")
    }
    var minute by  remember {
        mutableStateOf("")}
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    var selectedPriority by remember { mutableStateOf("None") }

    Scaffold(modifier = Modifier.padding(top = 30.dp),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { /* Handle back button click */ },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(
                            text = "AddTask",
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(48.dp)) // Adjust spacing if needed
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(50.dp))

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
//                val Date
                DateContent(onDateSelected = { selectedDate = it })
//Spacer(modifier = Modifier.width(5.dp))
                var (hourValue, minuteValue) = TimeContent()
                hour = if(hourValue<10) "0${hourValue}" else "$hourValue"
                minute = if(minuteValue<10) "0${minuteValue}" else "$minuteValue"


            }

//            var (hourValue, minuteValue) = TimeContent()
//            hour = if(hourValue<10) "0${hourValue}" else "$hourValue"
//            minute = if(minuteValue<10) "0${minuteValue}" else "$minuteValue"

            // Priority
            PrioritySelector(
                modifier = Modifier.fillMaxWidth(),
                priority = selectedPriority,
                onPrioritySelected = { selectedPriority = it }
            )

            // Category Dropdown
            CategoryDropdown(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                category = category,
                onCategorySelected = { /* Handle category selection */ }
            )

            // Subtasks
            Subtasks(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                subtasks = subtasks,
                onSubtasksChanged = { /* Handle subtasks change */ }
            )

            IconButton(onClick = { subtasks.add("") }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Subtask"
                )
            }
        }
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
    val outlineBorder = BorderStroke(1.dp, Color.Black)
    var timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute
    )

    // Show the time picker dialog when showDialog is true
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timePickerState)
                Row(
                    modifier = Modifier.padding(top = 6.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Dismiss")
                    }
                    TextButton(onClick = {
                        showDialog = false
                        // Update selectedHour and selectedMinute when Confirm is clicked
                        selectedHour = timePickerState.hour
                        selectedMinute = timePickerState.minute
                    }) {
                        Text(text = "Confirm")
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
//            contentAlignment = Alignment.CenterEnd
        )  {
            OutlinedTextField(
                value = String.format("%02d:%02d", selectedHour, selectedMinute),
                onValueChange = { },
                modifier = Modifier
                    .width(210.dp)
                    .height(50.dp).border(0.1.dp, Color.Gray,shape = cornerShape), // Remove the duplicated modifier here
                shape = cornerShape,
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                enabled = false, // Disable editing
                trailingIcon = {
                    // Icon button to open the time picker dialog
                    IconButton(
                        onClick = { showDialog = true },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = "Time Picker Icon"
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
fun DateContent(onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val mYear = calendar.get(Calendar.YEAR)
    val mMonth = calendar.get(Calendar.MONTH)
    val mDay = calendar.get(Calendar.DAY_OF_MONTH)
    val cornerShape = RoundedCornerShape(3.dp)
    val borderWidth = 0.1.dp
    val outlineBorder = BorderStroke(1.dp, Color.Black)
    calendar.set(mYear, mMonth, mDay)

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis
    )
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableStateOf(calendar.timeInMillis)
    }

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
                value = SimpleDateFormat("dd MMM yyyy").format(Date(selectedDate)),
                onValueChange = {}, // No-op, since this is for display only
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp).border(0.1.dp, Color.Gray,shape = cornerShape),
                shape = cornerShape,
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                enabled = false, // Disable editing
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date Picker Icon",
                            tint = Color.Black // Set icon color to black
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
                        selectedDate = datePickerState.selectedDateMillis ?: calendar.timeInMillis
                        onDateSelected(SimpleDateFormat("dd-MMM-yyyy").format(Date(selectedDate)))
                    }) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text(text = "Cancel")
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
    priority: String,
    onPrioritySelected: (String) -> Unit
): String {
    val priorities = listOf("None", "High", "Medium", "Low")
    var selectedPriority by remember { mutableStateOf(priorities[0]) }
    var expanded by remember { mutableStateOf(false) }

    // Create a black outline
    val outlineBorder = BorderStroke(0.1.dp, Color.Gray)
    Column(
        modifier = Modifier.fillMaxWidth().padding(start = 0.dp, top = 4.dp),
//       horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Priority",
            color = Color.Black,
            fontFamily = fontFamily,
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
                    value = selectedPriority,
                    onValueChange = { },
                    enabled = false,
                    modifier = Modifier.weight(1f).then(Modifier.fillMaxWidth()),
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Black,
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
                priorities.forEach { priority ->
                    DropdownMenuItem(
                        text ={ Text(
                            priority
                        )},
                        onClick = {
                            selectedPriority = priority
                            onPrioritySelected(priority)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    return selectedPriority
}



@Composable
fun CategoryDropdown(
    modifier: Modifier = Modifier,
    category: String,
    onCategorySelected: (String) -> Unit
) {

}

@Composable
fun Subtasks(
    modifier: Modifier = Modifier,
    subtasks: List<String>,
    onSubtasksChanged: (List<String>) -> Unit
) {
    // Implement subtasks
}
