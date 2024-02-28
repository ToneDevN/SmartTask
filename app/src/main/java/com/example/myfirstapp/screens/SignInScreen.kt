package com.example.myfirstapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.example.myfirstapp.DataClass.LoginClass
import com.example.myfirstapp.DataClass.SigninRequest
import com.example.myfirstapp.Screen
import com.example.myfirstapp.SharedPreferencesManager
import com.example.myfirstapp.TaskAPI
import com.example.myfirstapp.ui.theme.purple
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun validateInput (email: String, password: String): Boolean {
    return !email.isNullOrEmpty() && !password.isNullOrEmpty()
}

@Composable
fun EmailPasswordContent(email: String, onEmailChange: (String) -> Unit,
                         Password: String, onPassword: (String) -> Unit) {
    Column {
        Text(text = "Email",color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(value = email, onValueChange = onEmailChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        Text(text = "Password",color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(value = Password, onValueChange = onPassword,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun SignInScreen(navController: NavHostController) {
    val contextForToast = LocalContext.current.applicationContext

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }
    val createClient = TaskAPI.create()
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState){
        when (lifecycleState){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                if (sharedPreferences.isLoggedIn){
                    navController.navigate(Screen.Home.route)
                }
                if (!sharedPreferences.userEmail.isNullOrEmpty()){
                    email = sharedPreferences.userEmail ?: "No Token"
                }
            }
        }

    }

   Column(modifier = Modifier
       .fillMaxSize()
       .padding(32.dp),
       verticalArrangement = Arrangement.Center
       ) {
        Text(text = "Sign In",
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
        )
       EmailPasswordContent(
           email = email, onEmailChange = {
               email = it
               isButtonEnabled = validateInput(email, password)
                                          },
           Password = password, onPassword = {
               password = it
               isButtonEnabled = validateInput(email, password)
           }
       )
       Spacer(modifier = Modifier.height(height = 32.dp))
       Button(onClick = {
           val signinRequest = SigninRequest(email, password)
           createClient.signin(signinRequest)
               .enqueue(object: Callback<LoginClass> {
                   @SuppressLint("RestrictedApi")
                   override fun onResponse(call: Call<LoginClass>, response: Response<LoginClass>) {
                       if(response.isSuccessful){
                           val user = response.body()
                           if(user != null){
                               // Login successful
                               sharedPreferences.isLoggedIn = true
                               sharedPreferences.userEmail = email
                               sharedPreferences.token = response.body()!!.token

                               Toast.makeText(contextForToast, "Login successful", Toast.LENGTH_SHORT).show()

                               // Navigate to the desired destination
                               if (navController.currentBackStack.value.size >= 2) {
                                   navController.popBackStack()
                               }
                               navController.navigate(Screen.Home.route)
                           } else {
                               // Token is empty or null, indicating login failure
                               Toast.makeText(contextForToast, "Email or Password incorrect.", Toast.LENGTH_SHORT).show()
                           }
                       } else {
                           // Response not successful (e.g., HTTP error)
                           Toast.makeText(contextForToast, "Email not found ${email}", Toast.LENGTH_SHORT).show()
                       }
                   }

                   override fun onFailure(call: Call<LoginClass>, t: Throwable) {
                       // Network request failed
                       Toast.makeText(contextForToast, "Error onFailure", Toast.LENGTH_SHORT).show()
                   }
               })
       },
           modifier = Modifier
               .fillMaxWidth(),
           shape = RoundedCornerShape(5.dp),
           colors = ButtonDefaults.buttonColors(containerColor = purple )
       ) {
           Text("Sign In",
               fontSize = 20.sp)
       }
       TextButton(
           onClick = { /*TODO*/ },
           modifier = Modifier.fillMaxWidth(),
           contentPadding = PaddingValues()
       ) {
           Text(
               "Forgot password?",
               color = Color.Gray,
               fontSize = 15.sp,
               textDecoration = TextDecoration.None,
               modifier = Modifier.drawWithContent {
                   drawContent()
                   drawLine(
                       color = Color.Gray,
                       start = Offset(0f, size.height),
                       end = Offset(size.width, size.height),
                       strokeWidth = 2f
                   )
               }
           )
       }
       Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
       Button(onClick = {
           if (navController.currentBackStack.value.size >= 2){
               navController.popBackStack()
           }
           navController.navigate(Screen.Create.route)
       },
           modifier = Modifier
               .fillMaxWidth(),
           shape = RoundedCornerShape(5.dp),
           colors = ButtonDefaults.buttonColors(containerColor =Color(0xFFECECEC)  )
       ) {
           Text("Create Account",
               color = Color.Gray,
               fontSize = 20.sp)
       }
   }

}