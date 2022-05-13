package com.fatahapps.pulatest


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatahapps.pulatest.destinations.QuestionsPageDestination
import com.fatahapps.pulatest.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph(start = true)
@Destination
@Composable
fun OnboardingPage(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val configuration = LocalConfiguration.current

        val width = configuration.screenWidthDp.dp
        val height = configuration.screenHeightDp.dp

        Box(modifier = Modifier
            .height(height /2f)
            .fillMaxWidth()
            .background(BackgroundGrey)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pula_logo),
                    contentDescription = "Pula Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(height /8)
                        .width(width /2)
                )

                Image(
                    painterResource(id = R.drawable.checklist),
                    contentDescription = "Checklist Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(height/3)
                )
            }
        }

        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Fill out the Pula Farmers Questionnaire",
                    style = TextStyle(
                        fontFamily = latoFonts,
                        fontWeight = FontWeight.W600,
                        fontSize = 30.sp
                    ),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "We de-risk your investments and build resilience",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                Spacer(modifier = Modifier.size(24.dp))
                Button(
                    onClick = {
                              navigator.navigate(QuestionsPageDestination("Question"))
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
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.button
                    )
                }
            }

        }
    }
}