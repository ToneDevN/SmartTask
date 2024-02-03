package com.example.myfirstapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myfirstapp.R
import com.example.myfirstapp.ui.theme.fontFamily
import com.example.myfirstapp.ui.theme.purple

@Composable
fun FirstScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 50.dp)
        )
        {
            val (image,text1,text2, button1, button2) = createRefs()
            Image(painter = painterResource(id = R.drawable.imagetodolist), contentDescription = "Logo",
                modifier = Modifier
                    .aspectRatio(1.2f)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        centerHorizontallyTo(parent)
                    })
            Text(
                text = "Task Management &" + "To-Do List",
                modifier = Modifier
                    .constrainAs(text1) {
                        top.linkTo(image.bottom, margin = 15.dp)
                        centerHorizontallyTo(parent)
                    },
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )
            Text(
                text = "This productive tool is designed to help" +
                        " you better manage your task " +
                        "project-wise conveniently!",
                modifier = Modifier
                    .constrainAs(text2) {
                        top.linkTo(text1.bottom, margin = 15.dp)
                        centerHorizontallyTo(parent)
                    },
                fontSize = 16.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .constrainAs(button1) {
                        top.linkTo(text2.bottom, margin = 15.dp)
                        centerHorizontallyTo(parent)
                    }
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = purple )
            ) {
                Text("Let’s Start",
                    fontSize = 20.sp)
            }
            TextButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .constrainAs(button2) {
                        top.linkTo(button1.bottom, margin = 15.dp)
                        centerHorizontallyTo(parent)
                    }
                    .fillMaxWidth()) {
                Text("I have an account",
                    fontSize = 20.sp,
                    color = purple)

            }
        }

    }
}