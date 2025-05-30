package com.example.cloudswithtile.presentation.theme

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.example.cloudswithtile.presentation.HeartRateViewModel
import com.example.cloudswithtile.presentation.MintGreen
import com.example.cloudswithtile.presentation.cloudsfont2
import com.example.cloudswithtile.presentation.getCurrentTimeAndDate
import com.example.cloudswithtile.presentation.readInt
import com.example.cloudswithtile.presentation.saveInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MoodAddedScreen(modifier: Modifier = Modifier, navController: NavController) {
    val offsetY = remember { Animatable(0f) }
    val offsetX = remember { Animatable(70f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedContent(offsetY = offsetY, offsetX = offsetX, navController = navController)
    }
}

@Composable
fun AnimatedContent(
    offsetY: Animatable<Float, AnimationVector1D>,
    offsetX: Animatable<Float, AnimationVector1D>,
    navController: NavController,
    heartRateViewModel: HeartRateViewModel = viewModel()
) {
    var isHeartRateValid by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val heartRate by heartRateViewModel.heartRate.observeAsState()
    val currentDateTime = getCurrentTimeAndDate()

    val month = currentDateTime.substring(5, 7).toInt()
    val day = currentDateTime.substring(8, 10).toInt()
    val hour = currentDateTime.substring(11, 13).toInt()

    LaunchedEffect(Unit) {
        heartRateViewModel.startListening()
        offsetY.animateTo(-8f, animationSpec = tween(durationMillis = 800))
        delay(500)
        offsetX.animateTo(8f, animationSpec = tween(durationMillis = 1100))

        while (heartRate == null || heartRate!! <= 0.0f) {
            heartRateViewModel.updateHeartRate()
            delay(200L)
        }

        // Save heart rate and other details only if a valid heart rate is received
        heartRate?.let {
            saveInt(context, it.toInt(), "hr")
            saveInt(context, month, "month")
            saveInt(context, day, "day")
            saveInt(context, hour, "hour")
            isHeartRateValid = true
        }
    }

    val moodnumfr = readInt(context, "cat1mood").collectAsState(initial = 0).value
    val moodnumf = readInt(context, "cat2mood").collectAsState(initial = 0).value
    val moodnump = readInt(context, "cat2perc").collectAsState(initial = 0).value
    val hr = readInt(context, "hr").collectAsState(initial = 0).value

    Column(
        modifier = Modifier
            .padding(0.dp)
            .offset(0.dp, 26.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Mood entered!",
            fontSize = 24.sp,
            color = Color.White,
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .alpha(1f)
                .offset(y = offsetY.value.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { navController.navigate("chatgptscreen") },
            modifier = Modifier
                .alpha(1f)
                .offset(y = offsetX.value.dp - 10.dp)
                .height(60.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MintGreen),
            enabled = isHeartRateValid
        ) {
            Text(text = "Let's go!", color = Color.DarkGray)
        }
    }
}
