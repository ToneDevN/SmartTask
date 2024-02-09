package com.example.myfirstapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun CalendarDate() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 45.dp, height = 45.dp)

    ) {
        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "1",
                modifier = Modifier
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
fun CalendarWeek(){
    Row(modifier = Modifier.fillMaxSize()) {
        CalendarDate()
    }
}

@Composable
fun HomeScreen(){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Search()
        CalendarWeek()
    }
}