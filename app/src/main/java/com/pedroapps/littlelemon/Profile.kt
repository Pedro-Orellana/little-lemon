package com.pedroapps.littlelemon

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pedroapps.littlelemon.ui.theme.karlaFontFamily

@Composable
fun Profile(navController: NavHostController, preferences: SharedPreferences) {

    val firstName = preferences.getString("firstName", "John")
    val lastName = preferences.getString("lastName", "Doe")
    val email = preferences.getString("email", "email@gamil.com")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()
    ) {

        Header()
        Body(
            firstName = firstName ?: "John",
            lastName = lastName ?: "Doe",
            email = email ?: "email@gmail.com"
        )
        BottomButton(buttonText = "Log Out") {
            preferences.edit()
                .remove("firstName")
                .remove("lastName")
                .remove("email")
                .apply()

            navController.navigate("Onboarding")
        }
    }
}


@Composable
fun Body(firstName: String, lastName: String, email: String) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Personal information",
            textAlign = TextAlign.Center,
            fontFamily = karlaFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp, start = 12.dp)
        )
        TextInput(isEnabled = false, value = firstName, onValueChange = {}, label = "First name", isFirst = true)
        TextInput(isEnabled = false, value = lastName, onValueChange = {}, label = "Last Name")
        TextInput(isEnabled = false, value = email, onValueChange = {}, label = "Email")

    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
//    val context = LocalContext.current
//    val preferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    //Profile(preferences = preferences,)
}