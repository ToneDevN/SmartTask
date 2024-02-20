package com.example.myfirstapp.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myfirstapp.R
import com.example.myfirstapp.ui.theme.fontFamily
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClicked: () -> Unit,
    isUserSignedIn: Boolean,
    userName: String?,
    onSignOutClicked: () -> Unit
) {

    var selectedImageId by remember { mutableStateOf(R.drawable.icons8_test_account_30) }
    var imageUri by remember { mutableStateOf<Uri?>(null)}
    var galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let{
                imageUri = it
            }
        }
    )

    var isImageDialogOpen by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = onBackClicked,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Spacer(modifier = Modifier.width(100.dp))
                        Text(
                            text = "Setting",
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(bottom = 60.dp)
                    , // Increased bottom padding
            ) {
                Text(
                    text = "Account",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp, top = 50.dp),
                    fontFamily = fontFamily
                )

                if (isUserSignedIn) {
                    // Show user's personal image or default image
                    val imageModifier = Modifier
                        .size(60.dp)
                        .padding(vertical = 8.dp)
                    val image = painterResource(id = R.drawable.icons8_test_account_30) // Default image



                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                            Button(
                                onClick = { galleryLauncher.launch("image/*") },
                                modifier = Modifier
                                    .size(60.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Image(
                                    painter = if (imageUri != null) {
                                        rememberAsyncImagePainter(imageUri)
                                    } else {
                                        painterResource(id = R.drawable.icons8_test_account_100)
                                    },
                                    contentDescription = "User Image",
                                    modifier = Modifier.fillMaxSize(), // Make the image fill the entire Button
                                    alignment = Alignment.Center
                                )
                            }



                        // Logic for image selection dialog
//                            if (isImageDialogOpen) {
//                                AlertDialog(
//                                    onDismissRequest = { isImageDialogOpen = false },
//                                    title = { Text("Select Profile Image") },
//                                    text = { Text("Choose an image from your gallery") },
//                                    confirmButton = {
//                                        TextButton(
//                                            onClick = {
//                                                selectedImageId = R.drawable.icons8_calendar_30
//                                                isImageDialogOpen = false
//                                            }
//                                        ) {
//                                            Text("OK")
//                                        }
//                                    },
//                                    dismissButton = {
//                                        TextButton(
//                                            onClick = { isImageDialogOpen = false }
//                                        ) {
//                                            Text("Cancel")
//                                        }
//                                    }
//                                )
//                            }


                        Text(
                            text = "$userName",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp, top = 10.dp,start = 10.dp),
                            fontFamily = fontFamily
                        )
                        Spacer(modifier = Modifier.weight(1f)) // Pushes the "Sign Out" button to the end

                        Box(
                            modifier = Modifier
                                .height(35.dp)
                                .width(100.dp)
                                .padding(horizontal = 16.dp)
                                .background(
                                    Color(0xFFF1ECFF),
                                    shape = RoundedCornerShape(3.dp)
                                )
                                .clickable { onSignOutClicked() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sign Out",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,
                                color = Color(0xFF6A0DAD), // Set text color to purple
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                        }
                    }
// Image selection dialog
                    if (isImageDialogOpen) {
                        // Show your image selection dialog here
                        // For now, just close the dialog after a short delay
                        LaunchedEffect(Unit) {
                            delay(1000) // Delay for 1 second
                            isImageDialogOpen = false
                        }
                    }

                } else {
                    // Show sign-in button
                    TextButton(onClick = { /* Handle sign-in */ }) {
                        Text("Sign in")
                    }
                }
            }
            // App version
                Text(
                    text = "App Version 1.0",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(),
                    fontFamily  = fontFamily,
                    color = Color.DarkGray // Change the text color to DarkGray
                )
        }, bottomBar = {
            // BottomAppBar content
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .height(0.7.dp)
                        .fillMaxWidth(0.9f)
                        .background(Color.Gray)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(MaterialTheme.colors.surface)
                    .padding(27.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "App Version 1.0",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = fontFamily,
                    color = Color.DarkGray // Change the text color to DarkGray
                )
            }
        }
    )
}




