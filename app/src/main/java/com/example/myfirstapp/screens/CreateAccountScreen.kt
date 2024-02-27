
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfirstapp.R
import com.example.myfirstapp.ui.theme.fontFamily
import com.example.myfirstapp.ui.theme.purple

val Montserrat = FontFamily(
    Font(R.font.montserrat_semibold, FontWeight.SemiBold)
)
@Composable
fun EmailPasswordContent(
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onconfirmPasswordChange: (String) -> Unit
) {
    val cornerShape = RoundedCornerShape(3.dp)
    val borderWidth = 0.1.dp

    Column {
        Text(
            text = "First Name",
            color = Color.Gray,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            modifier = Modifier.fillMaxWidth().height(50.dp)
                .border(borderWidth, color = Color.Gray, shape = cornerShape),
            shape = cornerShape,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = "Last Name",
            color = Color.Gray,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            modifier = Modifier.fillMaxWidth().height(50.dp)
                .border(borderWidth, color = Color.Gray, shape = cornerShape),
            shape = cornerShape,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = "Email",
            color = Color.Gray,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth().height(50.dp)
                .border(borderWidth, color = Color.Gray, shape = cornerShape),
            shape = cornerShape,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Text(
            text = "Password",
            color = Color.Gray,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(borderWidth, color = Color.Gray, shape = cornerShape),
            shape = cornerShape,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Text(
            text = "Confirm Password",
            color = Color.Gray,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onconfirmPasswordChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(borderWidth, color = Color.Gray, shape = cornerShape),
            shape = cornerShape,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(navController: NavController) {
    val contextForToast = LocalContext.current.applicationContext

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    BoxWithConstraints(

    ) {
        val height = constraints.maxHeight
        val contentHeight = height * 0.8f
        Scaffold(
            modifier = Modifier.padding(top = 30.dp),
            topBar = {
                TopAppBar(
                    title = { Text("", fontFamily = fontFamily) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            ) {
                Spacer(modifier = Modifier.height(35.dp))

                // Centered SmartTask
                Text(
                    text = "SmartTask",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    fontFamily = Montserrat,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(60.dp))

                // Start-aligned Create Account
                Text(
                    text = "Create Account",
                    fontWeight = FontWeight.Normal,
                    fontSize = 36.sp,
                    fontFamily = fontFamily,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.Start)
                )

                EmailPasswordContent(
                    firstName = firstName,
                    onFirstNameChange = { firstName = it },
                    lastName = lastName,
                    onLastNameChange = { lastName = it },
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it },
                    confirmPassword = confirmPassword,
                    onconfirmPasswordChange = { confirmPassword = it }
                )

                Spacer(modifier = Modifier.height(height = 32.dp))
                Button(
                    onClick = { "" },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = purple)
                ) {
                    Text("Create Account", fontSize = 20.sp, fontFamily = fontFamily)
                }

                if (password != confirmPassword) {
                    Text(
                        text = "Passwords do not match",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                LaunchedEffect(key1 = Unit) {
                    if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                        // Save the user's account information
                        Toast.makeText(
                            contextForToast,
                            "Account created successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
