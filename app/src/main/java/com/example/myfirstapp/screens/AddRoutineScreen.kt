package com.example.myfirstapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapp.R
import com.example.myfirstapp.ui.theme.fontFamily
import com.example.myfirstapp.ui.theme.purple
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoutineScreen(){
    var taskTitle by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var subtasks by remember { mutableStateOf(mutableListOf("")) }
    var hour by remember { mutableStateOf("") }
    val minDateInMillis: Long = System.currentTimeMillis()
    var minute by remember {
        mutableStateOf("")
    }
    var selectedDateFrom by remember { mutableStateOf("") }
    var selectedDateTo by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

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
                                onClick = {""
                                    // navController.popBackStack()
                                }, // Navigate back
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                            Text(
                                text = "New Routine",
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(48.dp)) // Adjust spacing if needed
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
                        Box(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            DateFromContent(onDateFromSelected = { selectedDateFrom = it })
                        }
                        Box(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            DateToContent(
                                onDateToSelected = { selectedDateTo = it },
                                minSelectableDate = minDateInMillis
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            var (hourValue, minuteValue) = TimeContent()
                            hour = if (hourValue < 10) "0${hourValue}" else "$hourValue"
                            minute = if (minuteValue < 10) "0${minuteValue}" else "$minuteValue"
                        }
                        Box(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            // Priority
                            PrioritySelectorForRoutine(
                                modifier = Modifier.fillMaxWidth(),
                                priority = selectedPriority,
                                onPrioritySelected = { selectedPriority = it }
                            )
                        }
                    }



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
                        .padding(horizontal = 32.dp, vertical = 20.dp).background(Color.White), // Adjust vertical padding here
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {

                    Spacer(modifier= Modifier.width(10.dp))

                    Button(
                        onClick = { /* Handle Done button click */ },
                        modifier = Modifier.weight(0.8f)
                            .padding(end = 8.dp)
                            .border(3.dp, purple, RoundedCornerShape(3.dp))
                            .background(purple, RoundedCornerShape(3.dp)),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = purple)
                    ) {
                        Text(
                            text = "Done",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                    IconButton(
                        onClick = { /* Handle Remove button click */ },
                        modifier = Modifier.weight(0.2f)
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
fun DateFromContent(onDateFromSelected: (String) -> Unit) {
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
            text = "From",
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )

        // Outlined text field to display the selected date along with the icon
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            OutlinedTextField(
                value = SimpleDateFormat("dd MMM yyyy").format(Date(selectedDate)),
                onValueChange = {}, // No-op, since this is for display only
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .border(1.dp, Color.Gray, shape = cornerShape),
                shape = cornerShape,
                textStyle = TextStyle(
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
                        selectedDate = datePickerState.selectedDateMillis ?: calendar.timeInMillis
                        onDateFromSelected(SimpleDateFormat("dd-MMM-yyyy").format(Date(selectedDate)))
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
fun DateToContent(
    onDateToSelected: (String) -> Unit,
    minSelectableDate: Long
) {
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
    ) {
        Text(
            text = "To",
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            OutlinedTextField(
                value = SimpleDateFormat("dd MMM yyyy").format(Date(selectedDate)),
                onValueChange = {}, // No-op, since this is for display only
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .border(1.dp, Color.Gray, shape = cornerShape),
                shape = cornerShape,
                textStyle = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                enabled = false, // Disable editing
                trailingIcon = {
                    IconButton(
                        onClick = { showDatePicker = true },
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icons8_calendar_30),
                            contentDescription = "Time Picker Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        selectedDate = datePickerState.selectedDateMillis ?: calendar.timeInMillis
                        if (selectedDate >= minSelectableDate) {
                            onDateToSelected(SimpleDateFormat("dd-MMM-yyyy").format(Date(selectedDate)))
                        } else {
                            // Handle invalid date selection
                            // You can show a Snackbar or display an error message
                            // For now, we reset the selected date to the minimum selectable date
                            selectedDate = minSelectableDate
                            onDateToSelected(SimpleDateFormat("dd-MMM-yyyy").format(Date(minSelectableDate)))
                        }
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
fun PrioritySelectorForRoutine(
    modifier: Modifier = Modifier,
    priority: String,
    onPrioritySelected: (String) -> Unit
): String {
    val priorities = listOf("None", "High", "Medium", "Low")
    var selectedPriority by remember { mutableStateOf(priorities[0]) }
    var expanded by remember { mutableStateOf(false) }
    val cornerShape = RoundedCornerShape(3.dp)
    // Create a black outline
    val outlineBorder = BorderStroke(1.dp, Color.Gray)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 4.dp),
//       horizontalAlignment = Alignment.CenterHorizontally
        //horizontal = 0.dp, vertical = 5.dp
    ) {

        Text(
            text = "Priority",
            color = Color.Black,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(top=13.dp)
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
                    modifier = Modifier
                        .weight(1f).height(50.dp)
                        .border(1.dp, Color.Gray, shape = cornerShape)
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