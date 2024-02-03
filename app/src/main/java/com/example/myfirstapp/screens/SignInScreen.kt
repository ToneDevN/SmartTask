package com.example.myfirstapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapp.ui.theme.gray
import com.example.myfirstapp.ui.theme.purple

@Composable
fun EmailPasswordContent(email: String, onEmailChange: (String) -> Unit,
                         Password: String, onPassword: (String) -> Unit) {
    Column {
        Text(text = "Email",color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(value = email, onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth().height(50.dp),
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

@Composable
fun SignInScreen() {
    val contextForToast = LocalContext.current.applicationContext

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

   Column(modifier = Modifier
       .fillMaxSize()
       .padding(32.dp),
       verticalArrangement = Arrangement.Center
       ) {
        Text(text = "Sign In",
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
        )
       EmailPasswordContent(email = email, onEmailChange = {email = it}, Password = password, onPassword = {password = it})
       Spacer(modifier = Modifier.height(height = 32.dp))
       Button(onClick = { /*TODO*/ },
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
       Button(onClick = { /*TODO*/ },
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