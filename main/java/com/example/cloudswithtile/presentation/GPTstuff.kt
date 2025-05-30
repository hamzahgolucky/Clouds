package com.example.cloudswithtile.presentation

import android.util.Log

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.wear.compose.material.Text
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyColumnDefaults
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import kotlinx.coroutines.delay

class ChatGptViewModel : ViewModel() {
    var responseText by mutableStateOf("")
    var gptQuery by mutableStateOf("")

    @OptIn(BetaOpenAI::class)
    fun getGPTResponse() {
        viewModelScope.launch {
            val openAI = OpenAI("key") // Replace with your actual API key

            responseText = try {
                val chatCompletionRequest = ChatCompletionRequest(
                    model = ModelId("gpt-4"),
                    messages = listOf(
                        ChatMessage(
                            role = ChatRole.User,
                            content = gptQuery
                        )
                    )
                )
                val response = openAI.chatCompletion(chatCompletionRequest)
                println("Response: $response")
                val choices = response.choices
                if (choices.isNullOrEmpty()) {
                    "No response choices received"
                } else {
                    choices.first().message?.content ?: "Received null content"
                }
            } catch (e: java.net.UnknownHostException) {
                // Handle network issues (e.g., no internet)
                "Network Error: ${e.message}"
            } catch (e: java.net.SocketTimeoutException) {
                // Handle request timeouts
                "Timeout Error: ${e.message}"
            }  catch (e: Exception) {
                // Catch all other exceptions
                "ERROR: ${e.message ?: "Unknown error"}"
            }
        }
    }
}

@Composable
fun ChatGptScreen(navController: NavHostController, viewModel: ChatGptViewModel = viewModel()) {
    Log.d("count","1")
    val context = LocalContext.current
    var velocity = 0f

    val hour1 = readInt(context,"hour")
    val day1 = readInt(context,"day")
    val month1 = readInt(context, "month")
    val moodnum1 = readInt(context,"cat1mood")
    val moodnum21 = readInt(context,"cat2mood")
    val moodnumpc1 = readInt(context,"cat2perc")
    val moodhr = readInt(context,"hr")
    val hour2 = hour1.collectAsState(initial = 0).value
    val hour = hourConverter(hour2)
    val day = day1.collectAsState(initial = 0).value
    val month = month1.collectAsState(initial = 0).value
    val moodnum = moodnum1.collectAsState(initial = 0).value
    val moodnum2 = moodnum21.collectAsState(initial = 0).value
    val moodnumpc = moodnumpc1.collectAsState(initial = 0).value
    val moodnumpc2 = percscale(moodnumpc)
    //val hr = moodhr.collectAsState(initial = 0).value
    val hr = moodhr.collectAsState(initial = 0).value
    val hr2 = hrscale(hr)
    val moodstring = numtomoodconverter(moodnum)
    val mood2string = numtomood2converter(moodnum,moodnum2)

    val query = "i am having $moodstring, more specifically $moodnumpc2 $mood2string. The time is $hour 00 (24 hour time), it's day $day of the $month th month of the year and my heart rate is $hr2 at $hr beats per minute. Acknowledge how I am feeling first. Suggest the correlation between my mood, heart rate, day and time as well. Talk to me in a cheerful, friendly and lighthearted tone. passively nudge me into entering my mood more often. separate the paragraphs with meaningful titles. no more than 160 words."
    //val query = "i am having $moodstring, more specifically very $mood2string. The time is 20 00 (24 hour time), it's day 19 of the 2nd month of the year and my heart rate is high at 113 beats per minute. Acknowledge how I am feeling first. Suggest the correlation between my mood, heart rate, day and time as well. Talk to me in a cheerful, friendly and lighthearted tone. passively nudge me into entering my mood more often. separate the paragraphs with meaningful titles. no more than 160 words."
    LaunchedEffect(query) {
        Log.d("query" , query)
        viewModel.gptQuery = query;
    }

    val listState = rememberScalingLazyListState()
    Scaffold(positionIndicator = { PositionIndicator(scalingLazyListState = listState) })
    {
        val focusRequester = remember {
            FocusRequester()
        }
        val coroutineScope = rememberCoroutineScope()
        Box (contentAlignment = Alignment.TopCenter) {
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .onRotaryScrollEvent {
                        Log.d(
                            "speed",
                            (it.verticalScrollPixels * 2).toString()
                        )
                        coroutineScope.launch {
                            velocity = (it.verticalScrollPixels.toInt()).toFloat()
                            if (velocity > 0) {
                                if (velocity > 15f) {
                                    velocity = 15f
                                }
                                listState.scrollBy(velocity.toFloat())
                                for (i in 1..100) {
                                    delay(150)
                                    velocity *= 0.9f
                                    listState.scrollBy(velocity.toFloat())
                                    if (velocity < 0.001f) {
                                        break
                                    }
                                    Log.d("speed", (velocity).toString())
                                }
                            } else if (velocity < 0) {
                                if (velocity < -15f) {
                                    velocity = -15f
                                }
                                listState.scrollBy(velocity.toFloat())
                                for (i in 1..100) {
                                    delay(150)
                                    velocity *= 0.9f
                                    listState.scrollBy(velocity.toFloat())
                                    if (velocity > -0.001f) {
                                        break
                                    }
                                    Log.d("speed", (velocity).toString())
                                }
                            }
                        };true
                    }
                    .focusRequester(focusRequester)
                    .focusable(),
                scalingParams = ScalingLazyColumnDefaults.scalingParams(),
                verticalArrangement = Arrangement.spacedBy(space=5.dp),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(8) { i ->
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()

                    }
                    when (i) {
                        1 -> {
                            Button(onClick = { viewModel.getGPTResponse() }, modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(backgroundColor = MintGreen)) {
                                Text("Smart Insights!",
                                    fontSize = 20.sp,
                                    fontFamily = cloudsfont2,
                                    textAlign = TextAlign.Center,
                                    color = Color.DarkGray
                                )
                            }
                        }
                        2 -> {
                            Text("|\nv",
                                fontSize = 20.sp,
                                fontFamily = cloudsfont2,
                                textAlign = TextAlign.Center,
                            )
                        }
                        //4 -> {
                         //     Text(text = query)
                        //}
                        5 -> {
                            Text(
                                text = viewModel.responseText,
                                fontFamily = cloudsfont2,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                        }


                        6 -> {
                            Text(
                                text = "perc: $moodnumpc \nmood1:$moodstring \nmood2:$mood2string \nhour:$hour \nmonth:$month \nday:$day \nhr:$hr"
                            )
                        }
                    }
                }
            }
        }
    }

}

fun hourConverter(hour:Int): String {
    var answer = hour.toString()
    if (hour.toString().length==1) {
        answer = "0$hour"
    }
    return answer
}

fun hrscale(hr:Int): String {
    var answer = ""
    if (hr < 60)                            { answer = "worryingly low" }
    else if ((hr >= 60) and (hr < 70))      { answer = "low" }
    else if ((hr >= 70) and (hr < 100))     { answer = "normal" }
    else if ((hr >= 100) and (hr < 140))    { answer = "high" }
    else if (hr >= 140)                     { answer = "worryingly high" }
    return answer
}

fun percscale(moodnumpc:Int): String {
    var answer = ""
    if (moodnumpc < 20) { answer = "just a bit" }
    else if ((moodnumpc >= 20) and (moodnumpc < 40)) { answer = "kind of" }
    else if ((moodnumpc >= 40) and (moodnumpc < 60)) { answer = "pretty" }
    else if ((moodnumpc >= 60) and (moodnumpc < 80)) { answer = "very" }
    else if ((moodnumpc >= 80) and (moodnumpc <= 100)) { answer = "extremely" }
    return answer
}