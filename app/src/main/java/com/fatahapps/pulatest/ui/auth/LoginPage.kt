package com.fatahapps.pulatest.ui.auth

import android.view.GestureDetector
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fatahapps.pulatest.destinations.QuestionsPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun LoginPage(
    navigator: DestinationsNavigator
) {
    Column (
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        val context = LocalContext.current

        var phone by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = phone,
            onValueChange = {
                phone = it
            },
            label = { Text("Phone Number") },
            singleLine = true,
            placeholder = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            trailingIcon = {
                Icon(imageVector = Icons.Filled.Phone, contentDescription = "phoneIcon")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(20.dp))
        var password by rememberSaveable { mutableStateOf("") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }

        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = { Text("Password") },
            singleLine = true,
            placeholder = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (phone.text.isEmpty()) {
                    Toast.makeText(context, "Enter Phone Number", Toast.LENGTH_LONG)
                        .show()
                } else if (!phone.text.startsWith("+254")) {
                    Toast.makeText(context, "Number should start with +254...", Toast.LENGTH_LONG)
                        .show()
                } else if (phone.text.length != 13) {
                    Toast.makeText(context, "Phone Number should be 12 digits", Toast.LENGTH_LONG)
                        .show()
                } else if (password == "1234GYD%$") {
                    navigator.navigate(QuestionsPageDestination)
                } else {
                    Toast.makeText(context, "Wrong Password", Toast.LENGTH_LONG)
                        .show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }

}