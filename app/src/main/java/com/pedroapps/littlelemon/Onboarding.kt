package com.pedroapps.littlelemon

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pedroapps.littlelemon.ui.theme.karlaFontFamily


@Composable
fun Onboarding(navController: NavHostController, preferences: SharedPreferences) {

    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current

    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    val onClickHandler: () -> Unit = {
        var message = "Registration successful!"
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
            message = "Registration unsuccessful. Please enter all data."
        } else {
            preferences.edit()
                .putString("firstName", firstName)
                .putString("lastName", lastName)
                .putString("email", email)
                .apply()
            navController.navigate("Home")
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures (onTap = {
                    localFocusManager.clearFocus()
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        OnboardingHeader()

        InformationForm(firstName = firstName,
            setFirstName = { firstName = it },
            lastName = lastName,
            setLastName = { lastName = it },
            email = email,
            setEmail = { email = it })


        BottomButton(buttonText = "Register", onClickHandler = onClickHandler)


    }
}


@Composable
fun InformationForm(
    firstName: String, setFirstName: (String) -> Unit,
    lastName: String, setLastName: (String) -> Unit, email: String, setEmail: (String) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .wrapContentSize(Alignment.TopCenter)

    ) {
        Text(
            text = "Personal information",
            fontFamily = karlaFontFamily,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 12.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        TextInput(
            isEnabled = true,
            value = firstName,
            onValueChange = setFirstName,
            label = "First name",
            isFirst = true,

        )
        TextInput(
            isEnabled = true,
            value = lastName,
            onValueChange = setLastName,
            label = "Last name"
        )
        TextInput(isEnabled = true, value = email, onValueChange = setEmail, label = "Email")

    }
}


