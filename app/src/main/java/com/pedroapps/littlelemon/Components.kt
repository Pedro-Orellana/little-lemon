package com.pedroapps.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "little lemon logo", contentScale = ContentScale.Fit,
            modifier = Modifier.size(width = 200.dp, height = 100.dp)
        )
    }
}

@Composable
fun OnboardingHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "little lemon logo", contentScale = ContentScale.Fit,
            modifier = Modifier.size(width = 200.dp, height = 100.dp)
        )

        Column(
            modifier = Modifier
                .background(Color(0xFF495E57))
                .fillMaxWidth()
                .size(120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                "Let's get to know you",
                color = Color.White, textAlign = TextAlign.Center, fontSize = 24.sp
            )
        }
    }
}

@Composable
fun HomeHeader(handleProfileClick: () -> Unit) {
    Box( modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "little lemon logo", contentScale = ContentScale.Fit,
            modifier = Modifier.size(width = 200.dp, height = 100.dp).align(Alignment.Center)
        )

        Image(painter = painterResource(id = R.drawable.profile), contentDescription = "Profile picture",
            contentScale = ContentScale.FillBounds, modifier = Modifier.padding(end = 20.dp).size(50.dp)
                .align(Alignment.CenterEnd).clickable{handleProfileClick()})
    }
}


@Composable
fun BottomButton(buttonText: String, onClickHandler: () -> Unit) {
    Column {
        Button(
            onClick = (onClickHandler),
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, bottom = 20.dp)
                //.wrapContentSize(Alignment.BottomCenter)
                .fillMaxWidth()
                .weight(1f, false),
            colors = ButtonDefaults.textButtonColors(containerColor = Color(0xFFF4CE14))
        ) {
            Text(text = buttonText, textAlign = TextAlign.Center, color = Color.Black)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    isEnabled: Boolean, value: String,
    onValueChange: (String) -> Unit, label: String, isFirst: Boolean = false
) {
    val padding =
        if (isFirst) PaddingValues(start = 12.dp, end = 12.dp, bottom = 30.dp, top = 60.dp)
        else PaddingValues(start = 12.dp, end = 12.dp, bottom = 30.dp)
    OutlinedTextField(
        value = value, onValueChange = (onValueChange),
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        label = ({ Text(text = label) }), shape = CircleShape, enabled = isEnabled
    )

}