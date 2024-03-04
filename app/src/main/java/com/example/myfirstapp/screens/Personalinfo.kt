package com.example.myfirstapp.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.myfirstapp.DataClass.User
import com.example.myfirstapp.SharedPreferencesManager
import com.example.myfirstapp.TaskAPI
import com.example.myfirstapp.ui.theme.purpleLight
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun FramedImage(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        OutlinedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .size(width = 60.dp, height = 60.dp),
            colors = CardDefaults.cardColors(containerColor = purpleLight),
            shape = CircleShape,
            border = BorderStroke(1.5.dp, Color.Black),

        ){
            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null,Modifier.size(50.dp))
            }
        }
    }
}

@Composable
fun CompletedTask() {
    Box(modifier = Modifier
        .size(150.dp)
        .background(purpleLight)
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(unbounded = true, align = Alignment.Center)){
            Text(
                text = "0",
                fontSize = 64.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.Center)
            )
            Text(
                text = "Completed Task",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Personalinfo(navController: NavController){
    val chartEntryModel = entryModelOf(4f, 12f, 8f, 16f)
    val contextForToast = LocalContext.current.applicationContext
    var userState = remember { mutableStateOf<User?>(null) }
    var sharedPreferences: SharedPreferencesManager = SharedPreferencesManager(context = contextForToast)
    showUser(userState, contextForToast, sharedPreferences)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        ConstraintLayout (modifier = Modifier.fillMaxWidth()) {
            val (image,text) = createRefs()
            FramedImage(Modifier.padding(8.dp).constrainAs(image){top.linkTo(parent.top)})
            Text(text = " ${userState.value?.firstName.orEmpty()} ${userState.value?.lastName.orEmpty()}", fontWeight = FontWeight.SemiBold, fontSize = 24.sp, textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(text){start.linkTo(image.end, margin = 8.dp)
                    centerVerticallyTo(image)
                })
        }
        Text(text = "Task Overview", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            CompletedTask()
            CompletedTask()
        }
        Chart(
            chart = lineChart(),
            model = chartEntryModel,
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(),
        )
    }
}


fun showUser(userState: MutableState<User?>, context: Context, sharedPreferences: SharedPreferencesManager) {
    val createClient = TaskAPI.create()
    createClient.getUser("Bearer ${sharedPreferences.token}")
        .enqueue(object: Callback<User?> {
            @SuppressLint("RestrictedApi")
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if(response.isSuccessful){
                    var user = response.body()
                    if(user != null){
                        userState.value = user
                    } else {
                        // Token is empty or null, indicating login failure
                        Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Response not successful (e.g., HTTP error)
                    Toast.makeText(context, "Category not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                // Network request failed
                Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show()
            }
        })
}

//fun showTask(
//
//)