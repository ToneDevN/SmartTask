package com.example.myfirstapp.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.myfirstapp.DataClass.ListTask
import com.example.myfirstapp.DataClass.Task
import com.example.myfirstapp.DataClass.TaskX
import com.example.myfirstapp.DataClass.TaskXX
import com.example.myfirstapp.DataClass.weekTask
import com.example.myfirstapp.DataClass.weekTaskX
import com.example.myfirstapp.Screen
import com.example.myfirstapp.SharedPreferencesManager
import com.example.myfirstapp.TaskAPI
import com.example.myfirstapp.ui.theme.fontFamily
import com.example.myfirstapp.ui.theme.gray
import com.example.myfirstapp.ui.theme.purple
import com.example.myfirstapp.ui.theme.purpleLight
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search() {

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val SearchHistory = listOf("Android", "Kotlin", "Compose")
    DockedSearchBar(
        query = query,
        onQueryChange = { query = it},
        onSearch = { newQuery -> print("Performing search on query $newQuery") },
        active = active,
        onActiveChange = {active = it},
        placeholder = { Text(text = "Search")},
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search") },
        trailingIcon = if (active) {
            {
                IconButton(onClick = { if (query.isNotEmpty()) query = "" else active = false }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                }
            }
        } else null,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),

        ) {
        SearchHistory.takeLast(3).forEach{item ->
            ListItem(modifier = Modifier.clickable { query = item },
                headlineContent = { Text(text = item)},
                leadingContent = {
                }
            )
        }

    }

}

@Composable
fun CalendarDate(day: String, TaskStatus: Boolean = false,Date:String) {
    val contextForToast = LocalContext.current.applicationContext
    val createClient = TaskAPI.create()
    var sharedPreferences: SharedPreferencesManager = SharedPreferencesManager(context = contextForToast)
    ConstraintLayout(
//        modifier = Modifier.fillMaxWidth()
    ){
        val (card,circle) = createRefs()
        ElevatedCard(

            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .size(width = 45.dp, height = 45.dp)
                .constrainAs(card) {
                    top.linkTo(parent.top, margin = 15.dp)
                },
            colors = CardDefaults.cardColors(containerColor = purpleLight),
            shape = CircleShape,
            onClick = {
                    GlobalVariables.tasks = emptyList()
                    try {
                        createClient.getListTaskByDate("Bearer ${sharedPreferences.token}", "${Date}")
                            .enqueue(object : Callback<ListTask> {
                                override fun onResponse(call: Call<ListTask>, response: Response<ListTask>) {
                                    if (response.isSuccessful) {
                                        val listTask = response.body()?.tasks
                                        GlobalVariables.tasks = listTask ?: emptyList()

                                    } else {
                                        showToast(contextForToast, "Failed to fetch tasks")
                                    }
                                }
                                override fun onFailure(call: Call<ListTask>, t: Throwable) {
                                    showToast(contextForToast, "Error: Fail 1 ${t.message}")
                                }
                            })
                    } catch (e: Exception) {
                        showToast(contextForToast, "Error: Fail 2 ${e.message}")
                    }
            }

        ) {
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = day,
                    modifier = Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }
        if(TaskStatus){
            Canvas(modifier = Modifier
                .size(height = 2.dp, width = 2.dp)
                .constrainAs(circle) {
                    top.linkTo(card.bottom, margin = 10.dp)
                    centerHorizontallyTo(card)

                }){
                drawCircle(gray, radius = 5.dp.toPx())
            }
        }


    }

}

@Composable
fun CalendarWeek(context: Context,createClient: TaskAPI, token: String?){
    val weekTaskState = remember { MutableLiveData<List<TaskX>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            createClient.getWeekHasTask("Bearer ${token}", "2024-02-18")
                .enqueue(object : Callback<weekTaskX> {
                    override fun onResponse(call: Call<weekTaskX>, response: Response<weekTaskX>) {
                        if (response.isSuccessful) {
                            val listWeekTask = response.body()?.task
                            if (listWeekTask != null) {
                                weekTaskState.value = listWeekTask
                            } else {
                                Log.d("Error WeekTask", "Empty response body")
                                showToast(context, "Failed to fetch tasks: Empty response body")
                            }
//                            showToast(context, "Week Task${listWeekTask}")
                        } else {
                            Log.d("Error WeekTask", response.message())
//                            showToast(context, "Failed to fetch tasks: ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<weekTaskX>, t: Throwable) {
                        Log.d("Error WeekTask", "Error: Fail 1 ${t.message}")
                        showToast(context, "Error: Fail 1 ${t.message}")
                    }
                })
        } catch (e: Exception) {
            Log.d("Error WeekTask", "Error: ${e.message}")
            showToast(context, "Error: ${e.message}")
        }
    }
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outputFormat1 = DateTimeFormatter.ofPattern("dd")
    val outputFormat2 = DateTimeFormatter.ofPattern("yyyy-MM-dd")



    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        weekTaskState.value?.forEachIndexed{index, taskX ->
            val dateString = taskX.date.orEmpty()
            val date = LocalDateTime.parse(dateString, inputFormat)
            val formattedNo = date.format(outputFormat1)
            val formattedDate = date.format(outputFormat2)
            val TaskStatus = taskX.todoList.isNotEmpty()
            CalendarDate(formattedNo.toString(), TaskStatus, formattedDate.toString())
        }

    }
}

@Composable
fun CustomLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = purple,
    backgroundColor: Color = purpleLight,
    clipShape: Shape = RoundedCornerShape(16.dp)
) {
    Box(
        modifier = modifier
            .clip(clipShape)
            .background(backgroundColor)
            .height(10.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(progressColor)
                .fillMaxHeight()
                .fillMaxWidth(progress)
        )
    }
}

@Composable
fun Tasks(task: Task,
    navController: NavController
){

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
//            .size(width = 240.dp, height = 100.dp)
            .fillMaxWidth()
            .height(150.dp),

        onClick =  {
            navController.currentBackStackEntry?.savedStateHandle?.set("data",
                Task(
                    task.taskID,task.title,task.description,
                    task.url,task.location,task.priority,
                    task.date,task.time,task.completed,
                    task.subtasks,task.categories,task.categoryID,
                    task.routineID
                )
            )
//            navController.currentBackStackEntry?.savedStateHandle?.set("task", task)
            navController.navigate(Screen.TaskDetail.route)
        },

        colors = CardDefaults.cardColors(containerColor = gray)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (text1, text2, text3, bar) = createRefs()
            Text(
                text = "${task.title}",
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(text1) {
                        top.linkTo(parent.top)
                    },
                textAlign = TextAlign.Center,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White
            )
            Text(text = "URL: ${task.url}",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(text2) {
                        top.linkTo(text1.bottom)
                    },
                textAlign = TextAlign.Center,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = Color.White
            )
//            CustomLinearProgressIndicator(progress = 0.5f, modifier = Modifier
//                .constrainAs(bar) {
//                    start.linkTo(text3.end)
//                    centerVerticallyTo(text3)
//                    bottom.linkTo(parent.bottom, margin = 14.dp)
//                }
//                .width(200.dp)
//
//            )
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = DateTimeFormatter.ofPattern("dd MMM")

            val dateString = task.date.orEmpty()
            val date = LocalDateTime.parse(dateString, inputFormat)
            val formattedDate = date.format(outputFormat)

if(task.date.orEmpty()==null){
    Text(text = "Due on Not Deifine",
    modifier = Modifier
        .padding(horizontal = 16.dp)
        .constrainAs(text3) {
            bottom.linkTo(parent.bottom, margin = 16.dp)
        },
    textAlign = TextAlign.Center,
    fontFamily = fontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
    color = Color.White
)}
else{
    Text(text = "Due on ${formattedDate}",
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .constrainAs(text3) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
            },
        textAlign = TextAlign.Center,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        color = Color.White
    )
}

        }
    }
}
@Composable
fun AllTasks(tasks: List<Task>, navController: NavController) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(space = 24.dp)
    ) {
        itemsIndexed(tasks) { index, task ->
            Tasks(task, navController)
        }
    }
}

//@Composable
//fun HomeScreen(navController:NavController){
//
//    Column (
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp),
//        horizontalAlignment = Alignment.Start
//    ) {
//        Search()
//        CalendarWeek()
//        Text(text = "Ongoing Tasks", fontSize = 26.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily, modifier = Modifier.padding(vertical = 16.dp))
//        AllTasks(navController)
//
//    }
//}

object GlobalVariables {
    var tasks by mutableStateOf<List<Task>>(emptyList())
    var templateStateData by mutableStateOf<TaskXX?>(null)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val createClient = TaskAPI.create()
    val contextForToast = LocalContext.current.applicationContext
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    val formattedDate = SimpleDateFormat("yyyy mm dd").format(Date(selectedDate))
    var userState = remember { mutableStateListOf<ListTask>() }
    var sharedPreferences: SharedPreferencesManager = SharedPreferencesManager(context = contextForToast)
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState){
        when (lifecycleState){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
//                showTask(userState, contextForToast, sharedPreferences, formattedDate)
            }
        }

    }
    sharedPreferences = SharedPreferencesManager(context = contextForToast)


    // Fetch tasks
    LaunchedEffect(Unit) {
        try {
            createClient.getListTask("Bearer ${sharedPreferences.token}")
                .enqueue(object : Callback<ListTask> {
                    override fun onResponse(call: Call<ListTask>, response: Response<ListTask>) {
                        if (response.isSuccessful) {
                            val listTask = response.body()?.tasks
                            GlobalVariables.tasks = listTask ?: emptyList()

                        } else {
                            showToast(contextForToast, "Failed to fetch tasks")
                        }
                    }
                    override fun onFailure(call: Call<ListTask>, t: Throwable) {
                        showToast(contextForToast, "Error: Fail 1 ${t.message}")
                    }
                })
        } catch (e: Exception) {
            showToast(contextForToast, "Error: Fail 2 ${e.message}")
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Search()
        CalendarWeek(contextForToast, createClient, sharedPreferences.token)
        Text(
            text = "Ongoing Tasks",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            modifier = Modifier.padding(vertical = 16.dp)
        )
//        Text(text = "${tasks}")
        AllTasks(GlobalVariables.tasks, navController)
    }}
//
//    fun showTask(userState: SnapshotStateList<ListTask>, context: Context, sharedPreferences: SharedPreferencesManager, formattedDate: String) {
//        val createClient = TaskAPI.create()
//        val Request = DateRequest(formattedDate)
//        createClient.getWeekHasTask("Bearer ${sharedPreferences.token}", Request)
//            .enqueue(object: Callback<ListTask?> {
//                @SuppressLint("RestrictedApi")
//                override fun onResponse(call: Call<ListTask?>, response: Response<ListTask?>) {
//                    if(response.isSuccessful){
//                        var user = response.body()
//                        if(user != null){
//                        } else {
//                            // Token is empty or null, indicating login failure
//                            Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show()
//                        }
//                    } else {
//                        // Response not successful (e.g., HTTP error)
//                        Toast.makeText(context, "Category not found", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<ListTask?>, t: Throwable) {
//                    // Network request failed
//                    Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }
//
//}

