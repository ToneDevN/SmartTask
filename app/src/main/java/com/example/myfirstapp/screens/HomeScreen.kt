package com.example.myfirstapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myfirstapp.ui.theme.fontFamily
import com.example.myfirstapp.ui.theme.gray
import com.example.myfirstapp.ui.theme.purple
import com.example.myfirstapp.ui.theme.purpleLight
import java.util.Calendar


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
fun CalendarDate(day: String) {
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
            shape = CircleShape

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

@Composable
fun CalendarWeek(){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        for (i in 1..7){
            CalendarDate(i.toString())
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
fun Tasks(){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
//            .size(width = 240.dp, height = 100.dp)
            .fillMaxWidth()
            .height(150.dp),

        colors = CardDefaults.cardColors(containerColor = gray)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (text1, text2, text3, bar) = createRefs()
            Text(
                text = "Mobile App Wireframe",
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
            Text(text = "URL: url...",
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
            CustomLinearProgressIndicator(progress = 0.5f, modifier = Modifier
                .constrainAs(bar){
                start.linkTo(text3.end)
                    centerVerticallyTo(text3)
                    bottom.linkTo(parent.bottom, margin = 14.dp)
                }
                .width(200.dp)

            )
            Text(text = "Due on 20 May",
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

@Composable
fun AllTasks(){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(space = 24.dp)
    ){
        item {
            Tasks()
        }
    }
    
}

@Composable
fun HomeScreen(){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Search()
        CalendarWeek()
        Text(text = "Ongoing Tasks", fontSize = 26.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily, modifier = Modifier.padding(vertical = 16.dp))
        AllTasks()
    }
}