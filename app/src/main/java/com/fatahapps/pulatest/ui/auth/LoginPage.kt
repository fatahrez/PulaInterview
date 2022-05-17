package com.fatahapps.pulatest.ui.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.GestureDetector
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.preference.PreferenceManager
import coil.compose.rememberImagePainter
import com.fatahapps.pulatest.ApplicationPrefs
import com.fatahapps.pulatest.MainActivity
import com.fatahapps.pulatest.destinations.QuestionsPageDestination
import com.fatahapps.pulatest.ui.theme.ColorButton
import com.fatahapps.pulatest.ui.theme.latoFonts
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

private lateinit var prefManager: ApplicationPrefs

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
        val activity = LocalContext.current as MainActivity
        prefManager = ApplicationPrefs()
        Log.i("TAG", "FirstTime: ${PreferenceManager.getDefaultSharedPreferences(context).getBoolean("wasAppStartedPreviously", false)}")

        val configuration = LocalConfiguration.current

        val width = configuration.screenWidthDp.dp
        val height = configuration.screenHeightDp.dp

        Image(
            painter = rememberImagePainter(
                data = "https://img.freepik.com/free-vector/happy-freelancer-with-computer-home-young-man-sitting-armchair-using-laptop-chatting-online-smiling-vector-illustration-distance-work-online-learning-freelance_74855-8401.jpg?t=st=1652717451~exp=1652718051~hmac=96a2f4e58b431acbd6b66de73a568a2e4fdd7c24779f065b8bca62295eb4fd70&w=2000"
            ),
            contentDescription = "Login page image",
            modifier = Modifier
                .width(width / 1.3f)
                .height(height / 3)
                .align(CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = "Login",
            style = TextStyle(
                fontFamily = latoFonts,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
            modifier = Modifier.padding(16.dp)
        )
        
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
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Phone, contentDescription = "phoneIcon")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            )
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
            leadingIcon = {
                  Icon(imageVector = Icons.Filled.LockOpen, "Lock icon")
            },
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
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
                } else if(!prefManager.isNotFirstTime() && password == "1234GYD%$"){
                    val recipient = "tech@pula.io"
                    val subject = "New User login"
                    val message = "User ${phone.text} has logged in for the first time"
                    sendEmail(recipient, subject, message, activity, context)
                    navigator.navigate(QuestionsPageDestination)
                } else if (password == "1234GYD%$") {
                    navigator.navigate(QuestionsPageDestination)
                } else {
                    Toast.makeText(context, "Wrong Password", Toast.LENGTH_LONG)
                        .show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorButton,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(30)
        ) {
            Text(text = "Login")
        }
    }

}

private fun sendEmail(
    recipient: String,
    subject: String,
    message: String,
    activity: MainActivity,
    context: Context
) {
    prefManager.setNotFirstTime(true)
    /*ACTION_SEND action to launch an email client installed on your Android device.*/
    val mIntent = Intent(Intent.ACTION_SEND)
    /*To send an email you need to specify mailto: as URI using setData() method
    and data type will be to text/plain using setType() method*/
    mIntent.data = Uri.parse("mailto:")
    mIntent.type = "text/plain"
    // put recipient email in intent
    /* recipient is put as array because you may wanna send email to multiple emails
       so enter comma(,) separated emails, it will be stored in array*/
    mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
    //put the Subject in the intent
    mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    //put the message in the intent
    mIntent.putExtra(Intent.EXTRA_TEXT, message)


    try {
        //start email intent
        activity.startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
    }
    catch (e: Exception){
        //if any thing goes wrong for example no email client application or any exception
        //get and show exception message
        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
    }

}