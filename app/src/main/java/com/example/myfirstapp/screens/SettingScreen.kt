package com.example.myfirstapp.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myfirstapp.DataClass.Category
import com.example.myfirstapp.DataClass.LoginClass
import com.example.myfirstapp.DataClass.Subtask
import com.example.myfirstapp.DataClass.Task
import com.example.myfirstapp.DataClass.User
import com.example.myfirstapp.R
import com.example.myfirstapp.Screen
import com.example.myfirstapp.SharedPreferencesManager
import com.example.myfirstapp.TaskAPI
import com.example.myfirstapp.ui.theme.fontFamily
import com.example.myfirstapp.ui.theme.purple
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController
) {

    val user = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")?:
    User("","","")


    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)
//    var isUserSignedIn = true;
    var  userName = user.firstName + user.lastName       ;
    var selectedImageId by remember { mutableStateOf(R.drawable.icons8_test_account_30) }

    var imageUri by remember { mutableStateOf(Uri.parse("http://10.199.120.148/upload/${user.profileImg}")) }


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
                            onClick = { navController.popBackStack() },
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
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Account",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp, top = 50.dp),
                    fontFamily = fontFamily
                )
                    // Show user's personal image or default image
                    val imageModifier = Modifier
                        .size(60.dp)
                        .padding(vertical = 8.dp)
                    val image = painterResource(id = R.drawable.icons8_test_account_30) // Default image

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
//                                .background(Color.Gray)
//                                .clickable { isImageDialogOpen = true }
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
//                                modifier = Modifier.background(Color.Green)
                            ) {
                                // Display the profile image here
                                Image(
                                    painter = if (user.profileImg != null) {
                                        rememberImagePainter(imageUri)
                                    } else {
                                        painterResource(id = R.drawable.icons8_test_account_100)
                                    },
                                    contentDescription = "User Image",
                                    modifier = Modifier.aspectRatio(1f)
                                        .clickable { isImageDialogOpen = true }
                                    ,
                                    alignment = Alignment.Center
                                )
                                Spacer(modifier = Modifier.height(50.dp))
                                Button(
                                    onClick = {
                                        isImageDialogOpen = true
                                              },
                                    modifier = Modifier.padding(top = 8.dp),
                                    shape = RoundedCornerShape(3.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFFF1ECFF
                                        )
                                    ),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text(
                                        text = "Change Image",
                                        color = purple,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = fontFamily,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                        // Logic for image selection dialog
                        if(isImageDialogOpen) {
                            Card(
                                modifier = Modifier.padding(16.dp),
                                shape = RoundedCornerShape(8.dp) // Set the corner radius as needed
                            ) {
                                AlertDialog(
                                    onDismissRequest = { isImageDialogOpen = false },
                                    modifier = Modifier.padding(16.dp),
                                    properties = DialogProperties(usePlatformDefaultWidth = false)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .background(Color.White, RoundedCornerShape(8.dp))
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            "Select Profile Image",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Image(
                                            painter = if (imageUri != null) {
                                                rememberImagePainter(imageUri)
                                            } else {
                                                painterResource(id = R.drawable.icons8_test_account_100)
                                            },
                                            contentDescription = "User Image",
                                            modifier = Modifier
                                                .aspectRatio(1f)
                                                .clickable { galleryLauncher.launch("image/*") },
                                            alignment = Alignment.Center
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Row(
                                            horizontalArrangement = Arrangement.End,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {

                                            Button(
                                                onClick = { isImageDialogOpen = false },
                                                modifier = Modifier.padding(end = 8.dp)
                                            ) {
                                                Text("Cancel")
                                            }
                                            Button(
                                                onClick = { imageUri?.let { uri -> uploadImage(contextForToast, uri) };
                                                    isImageDialogOpen = false },
                                                modifier = Modifier.padding(top = 8.dp),
                                                shape = RoundedCornerShape(3.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(
                                                        0xFFF1ECFF
                                                    )
                                                ),
                                                contentPadding = PaddingValues(0.dp)
                                            ) {
                                                Text(
                                                    text = "Done",
                                                    color = purple,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = fontFamily,
                                                    modifier = Modifier.padding(8.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

//Spacer(modifier = Modifier.height(100.dp).background(Color.Black))
                        Text(
                            text = "$userName",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 20.dp),
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
                                .clickable {
                                    sharedPreferences.clearUserAll()
                                    Toast.makeText(contextForToast, "Logout!!", Toast.LENGTH_SHORT).show()
                                    if (navController.currentBackStack.value.size >= 2){
                                        navController.popBackStack()
                                    }
                                    navController.navigate(Screen.SignIn.route)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sign Out",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,
                                color = purple, // Set text color to purple
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                        }
                    }

                }


        }, bottomBar = {
            // BottomAppBar content
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(MaterialTheme.colors.surface)
                    .padding(27.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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



fun uploadImage(context: Context, uri: Uri) {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val imageFile = File(context.cacheDir, "temp_image_file")
    inputStream?.use { input ->
        FileOutputStream(imageFile).use { output ->
            input.copyTo(output)
        }
    }
    val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
    val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

    val apiService = TaskAPI.create()
    val call = apiService.uploadImage("Bearer ${SharedPreferencesManager(context).token}", imagePart)
    call.enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            // Handle success response
            println(response)
            if (response.isSuccessful) {
                Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to upload image server broken", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onFailure(call: Call<Void>, t: Throwable) {
            // Handle failure
            Toast.makeText(context, "Failed to upload image: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}
