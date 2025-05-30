package com.example.cloudswithtile.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyColumnDefaults
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import com.google.gson.Gson
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

data class Mood(
    val mood: String,
)







@Composable
fun StatOne(modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val listState = rememberScalingLazyListState()
    val scope = rememberCoroutineScope()
    val hour1 = readInt(context,"hour")
    val day1 = readInt(context,"day")
    val moodnum1 = readInt(context,"cat1mood")
    val moodnum21 = readInt(context,"cat2mood")
    val moodnumpc1 = readInt(context,"cat2perc")
    val moodhr = readInt(context,"hr")
    val hour = hour1.collectAsState(initial = 0).value
    val day = day1.collectAsState(initial = 0).value
    val moodnum = moodnum1.collectAsState(initial = 0).value
    val moodnum2 = moodnum21.collectAsState(initial = 0).value
    val moodnumpc = moodnumpc1.collectAsState(initial = 0).value
    val hr = moodhr.collectAsState(initial = 0).value
    val moodstring = numtomoodconverter(moodnum)
    Scaffold (positionIndicator = { PositionIndicator(scalingLazyListState=listState) })
    {
        val focusRequester = remember {
            FocusRequester()
        }
        val coroutineScope = rememberCoroutineScope()
        ScalingLazyColumn (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding()
                .onRotaryScrollEvent {
                    Log.d(
                        "speed",
                        (it.verticalScrollPixels * abs(it.verticalScrollPixels)).toString()
                    )
                    coroutineScope.launch {
                        listState.scrollBy(it.verticalScrollPixels * abs(it.verticalScrollPixels))
                    };true
                }
                .focusRequester(focusRequester)
                .focusable()
                .fillMaxSize(),
            scalingParams = ScalingLazyColumnDefaults.scalingParams(),
            verticalArrangement = Arrangement.spacedBy(space=0.dp),
            state = listState,){
            items(5) {i ->
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                when (i) {
                    0 -> {
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                    1 -> {
                        Text(
                            "Smart Insights!",
                            fontSize = 30.sp,
                            fontFamily = cloudsfont2,
                            textAlign = TextAlign.Center
                        )
                    }
                    2 -> {

                    }
                    3 -> {
                        MoodEntryScreen(viewModel(),moodstring)
                    }
                    4 -> {

                    }
                }
            }
        }

    }
}

fun getCurrentTimeAndDate(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return current.format(formatter)
}



@Composable
fun HeartRateScreen(heartRateViewModel: HeartRateViewModel = viewModel()) {
    val heartRate by heartRateViewModel.heartRate.observeAsState()

    LaunchedEffect(Unit) {
        heartRateViewModel.startListening()
        while (true) {
            heartRateViewModel.updateHeartRate()
            kotlinx.coroutines.delay(1000L)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Heart Rate: ${heartRate ?: "N/A"}")
    }
}








@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MoodBarGraph(viewModel: MoodViewModel = viewModel(),moodinput:String) {
    var isError = false;
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var moodstring by remember {
        mutableStateOf(moodinput)
    }
    if (moodstring.isNotEmpty()) {
        viewModel.addMood(context, moodstring)
        moodstring = ""
    }
    val moodData by viewModel.moodList.collectAsState()
    Text(moodstring)


    val moodCounts = moodData.groupBy { it }.mapValues { it.value.size }
    val maxCount = moodCounts.values.maxOrNull() ?: 0
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Your Clouds the past week!", textAlign = TextAlign.Center, fontFamily = cloudsfont2, modifier = Modifier.width(100.dp))
        Spacer(modifier = Modifier.height(15.dp))
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)) {
            val barWidth = size.width / moodCounts.size
            var xOffset = 0f

            moodCounts.forEach { (mood, count) ->
                val barHeight = (count / maxCount.toFloat()) * size.height

                drawRoundRect(
                    color = MintGreen,
                    topLeft = Offset(xOffset, size.height - barHeight),
                    size = Size(barWidth - 10.dp.toPx(), barHeight),
                    cornerRadius = CornerRadius(x = barWidth,y = barWidth)
                )


                xOffset += barWidth
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            moodCounts.keys.forEach { mood ->
                Text(text = mood.toString(), fontSize = 10.sp, fontFamily = cloudsfont2, modifier=Modifier.width(42.dp))
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.refreshMoodList(context)
    }

}





