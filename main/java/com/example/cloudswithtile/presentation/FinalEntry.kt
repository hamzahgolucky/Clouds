package com.example.cloudswithtile.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.example.cloudswithtile.R
import kotlinx.coroutines.launch
import kotlin.math.abs

val PastelLightRed = Color(0xFFFF7F7F)
val PastelPeach = Color(0xFFFFDAB9)
val PastelPurple = Color(0xFFDABFFF)
val PastelBlue = Color(0xFFB0C4DE)
val PastelPink = Color(0xFFFFD1DC)
val PastelYellow = Color(0xFFFFFEDC)
val PastelGreen = Color(0xFFD8FAD8)
val PastelOrange = Color(0xFFFFB899)
public val MintGreen = Color(0xFFB2FCC8)
val PastelLightBlue = Color(0xFFADD8E6)

@Composable
fun FinalEntry11(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var num = 0f
    val INT_KEY = "cat2perc"
    val offsetY = remember { Animatable(160f) }
    var isVis = true
    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVis) {
            // Animate to show
            offsetY.animateTo(120f, animationSpec = tween(durationMillis = 800))
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularSlider(
            modifier = Modifier.size(240.dp),
            padding = 0f,
            stroke = 30f,
            progressColor = PastelLightRed,
            thumbColor = Color.Blue,
            backgroundColor = Color.DarkGray
        ) {
            num = it
            if (num != 0f) {check=true}
        }
        if (check==false) {
            Image(
                painter = painterResource(R.drawable.cat1),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.cat1),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        scope.launch {
                            if (abs(num) > 1) {
                                num = 1F
                            } else if (num < 0) {
                                num = 0F
                            }
                            saveInt(context, (num * 100).toInt(), INT_KEY)
                        };navController.navigate("moodaddedscreen")
                    }
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        }
        Text("How much?",
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .size(100.dp)
                .absoluteOffset(0.dp, 80.dp))
    }
}
@Composable
fun FinalEntry22(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var num = 0f
    val INT_KEY = "cat2perc"
    val offsetY = remember { Animatable(160f) }
    var isVis = true
    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVis) {
        // Animate to show
        offsetY.animateTo(120f, animationSpec = tween(durationMillis = 800))
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularSlider(
            modifier = Modifier.size(240.dp),
            padding = 0f,
            stroke = 30f,
            progressColor = PastelPeach,
            thumbColor = Color.Blue,
            backgroundColor = Color.DarkGray
        ) {
            num = it
            if (num != 0f) {check=true}
        }
        if (check==false) {
            Image(
                painter = painterResource(R.drawable.cat2),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.cat2),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        scope.launch {
                            if (abs(num) > 1) {
                                num = 1F
                            } else if (num < 0) {
                                num = 0F
                            }
                            saveInt(context, (num * 100).toInt(), INT_KEY)
                        };navController.navigate("moodaddedscreen")
                    }
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        }
        Text("How much?",
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .size(100.dp)
                .absoluteOffset(0.dp, 80.dp))
    }
}
@Composable
fun FinalEntry33(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var num = 0f
    val INT_KEY = "cat2perc"
    val offsetY = remember { Animatable(160f) }
    var isVis = true
    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVis) {
        // Animate to show
        offsetY.animateTo(120f, animationSpec = tween(durationMillis = 800))
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularSlider(
            modifier = Modifier.size(240.dp),
            padding = 0f,
            stroke = 30f,
            progressColor = PastelPurple,
            thumbColor = Color.Blue,
            backgroundColor = Color.DarkGray
        ) {
            num = it
            if (num != 0f) {check=true}
        }
        if (check==false) {
            Image(
                painter = painterResource(R.drawable.cat3),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.cat3),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        scope.launch {
                            if (abs(num) > 1) {
                                num = 1F
                            } else if (num < 0) {
                                num = 0F
                            }
                            saveInt(context, (num * 100).toInt(), INT_KEY)
                        };navController.navigate("moodaddedscreen")
                    }
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        }
        Text("How much?",
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .size(100.dp)
                .absoluteOffset(0.dp, 80.dp))
    }
}
@Composable
fun FinalEntry44(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var num = 0f
    val INT_KEY = "cat2perc"
    val offsetY = remember { Animatable(160f) }
    var isVis = true
    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVis) {
        // Animate to show
        offsetY.animateTo(120f, animationSpec = tween(durationMillis = 800))
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularSlider(
            modifier = Modifier.size(240.dp),
            padding = 0f,
            stroke = 30f,
            progressColor = PastelBlue,
            thumbColor = Color.Blue,
            backgroundColor = Color.DarkGray
        ) {
            num = it
            if (num != 0f) {check=true}
        }
        if (check==false) {
            Image(
                painter = painterResource(R.drawable.cat4),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.cat4),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        scope.launch {
                            if (abs(num) > 1) {
                                num = 1F
                            } else if (num < 0) {
                                num = 0F
                            }
                            saveInt(context, (num * 100).toInt(), INT_KEY)
                        };navController.navigate("moodaddedscreen")
                    }
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        }
        Text("How much?",
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .size(100.dp)
                .absoluteOffset(0.dp, 80.dp))
    }
}
@Composable
fun FinalEntry55(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var num = 0f
    val INT_KEY = "cat2perc"
    val offsetY = remember { Animatable(160f) }
    var isVis = true
    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVis) {
        // Animate to show
        offsetY.animateTo(120f, animationSpec = tween(durationMillis = 800))
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularSlider(
            modifier = Modifier.size(240.dp),
            padding = 0f,
            stroke = 30f,
            progressColor = PastelPink,
            thumbColor = Color.Blue,
            backgroundColor = Color.DarkGray
        ) {
            num = it
            if (num != 0f) {check=true}
        }
        if (check==false) {
            Image(
                painter = painterResource(R.drawable.cat5),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.cat5),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        scope.launch {
                            if (abs(num) > 1) {
                                num = 1F
                            } else if (num < 0) {
                                num = 0F
                            }
                            saveInt(context, (num * 100).toInt(), INT_KEY)
                        };navController.navigate("moodaddedscreen")
                    }
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        }
        Text("How much?",
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .size(100.dp)
                .absoluteOffset(0.dp, 80.dp))
    }
}
@Composable
fun FinalEntry66(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var num = 0f
    val INT_KEY = "cat2perc"
    val offsetY = remember { Animatable(160f) }
    var isVis = true
    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVis) {
        // Animate to show
        offsetY.animateTo(120f, animationSpec = tween(durationMillis = 800))
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularSlider(
            modifier = Modifier.size(240.dp),
            padding = 0f,
            stroke = 30f,
            progressColor = PastelYellow,
            thumbColor = Color.Blue,
            backgroundColor = Color.DarkGray
        ) {
            num = it
            if (num != 0f) {check=true}
        }
        if (check==false) {
            Image(
                painter = painterResource(R.drawable.cat6),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.cat6),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        scope.launch {
                            if (abs(num) > 1) {
                                num = 1F
                            } else if (num < 0) {
                                num = 0F
                            }
                            saveInt(context, (num * 100).toInt(), INT_KEY)
                        };navController.navigate("moodaddedscreen")
                    }
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        }
        Text("How much?",
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .size(100.dp)
                .absoluteOffset(0.dp, 80.dp))
    }
}
@Composable
fun FinalEntry77(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var num = 0f
    val INT_KEY = "cat2perc"
    val offsetY = remember { Animatable(160f) }
    var isVis = true
    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVis) {
        // Animate to show
        offsetY.animateTo(120f, animationSpec = tween(durationMillis = 800))
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularSlider(
            modifier = Modifier.size(240.dp),
            padding = 0f,
            stroke = 30f,
            progressColor = PastelGreen,
            thumbColor = Color.Blue,
            backgroundColor = Color.DarkGray
        ) {
            num = it
            if (num != 0f) {check=true}
        }
        if (check==false) {
            Image(
                painter = painterResource(R.drawable.cat7),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.cat7),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        scope.launch {
                            if (abs(num) > 1) {
                                num = 1F
                            } else if (num < 0) {
                                num = 0F
                            }
                            saveInt(context, (num * 100).toInt(), INT_KEY)
                        };navController.navigate("moodaddedscreen")
                    }
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        }
        Text("How much?",
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .size(100.dp)
                .absoluteOffset(0.dp, 80.dp))
    }
}
@Composable
fun FinalEntry88(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var num = 0f
    val INT_KEY = "cat2perc"
    val offsetY = remember { Animatable(160f) }
    var isVis = true
    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVis) {
        // Animate to show
        offsetY.animateTo(120f, animationSpec = tween(durationMillis = 800))
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularSlider(
            modifier = Modifier.size(240.dp),
            padding = 0f,
            stroke = 30f,
            progressColor = PastelOrange,
            thumbColor = Color.Blue,
            backgroundColor = Color.DarkGray
        ) {
            num = it
            if (num != 0f) {check=true}
        }
        if (check==false) {
            Image(
                painter = painterResource(R.drawable.cat8),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.cat8),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        scope.launch {
                            if (abs(num) > 1) {
                                num = 1F
                            } else if (num < 0) {
                                num = 0F
                            }
                            saveInt(context, (num * 100).toInt(), INT_KEY)
                        };navController.navigate("moodaddedscreen")
                    }
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        }
        Text("How much?",
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .size(100.dp)
                .absoluteOffset(0.dp, 80.dp))
    }
}
@Composable
fun FinalEntry99(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var num = 0f
    val INT_KEY = "cat2perc"
    val offsetY = remember { Animatable(160f) }
    var isVis = true
    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVis) {
        // Animate to show
        offsetY.animateTo(120f, animationSpec = tween(durationMillis = 800))
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularSlider(
            modifier = Modifier.size(240.dp),
            padding = 0f,
            stroke = 30f,
            progressColor = MintGreen,
            thumbColor = Color.Blue,
            backgroundColor = Color.DarkGray
        ) {
            num = it
            if (num != 0f) {check=true}
        }
        if (check==false) {
            Image(
                painter = painterResource(R.drawable.cat9),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.cat9),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        scope.launch {
                            if (abs(num) > 1) {
                                num = 1F
                            } else if (num < 0) {
                                num = 0F
                            }
                            saveInt(context, (num * 100).toInt(), INT_KEY)
                        };navController.navigate("moodaddedscreen")
                    }
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        }
        Text("How much?",
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .size(100.dp)
                .absoluteOffset(0.dp, 80.dp))
    }
}
@Composable
fun FinalEntry1010(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var num = 0f
    val INT_KEY = "cat2perc"
    val offsetY = remember { Animatable(160f) }
    var isVis = true
    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVis) {
        // Animate to show
        offsetY.animateTo(120f, animationSpec = tween(durationMillis = 800))
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularSlider(
            modifier = Modifier.size(240.dp),
            padding = 0f,
            stroke = 30f,
            progressColor = PastelLightBlue,
            thumbColor = Color.Blue,
            backgroundColor = Color.DarkGray
        ) {
            num = it
            if (num != 0f) {check=true}
        }
        if (check==false) {
            Image(
                painter = painterResource(R.drawable.cat10),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.cat10),
                contentDescription = null,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable {
                        scope.launch {
                            if (abs(num) > 1) {
                                num = 1F
                            } else if (num < 0) {
                                num = 0F
                            }
                            saveInt(context, (num * 100).toInt(), INT_KEY)
                        };navController.navigate("moodaddedscreen")
                    }
                    .absoluteOffset(0.dp, -10.dp)
                    .alpha(1f)
                    .size(offsetY.value.dp)
            )
        }
        Text("How much?",
            fontFamily = cloudsfont2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .size(100.dp)
                .absoluteOffset(0.dp, 80.dp))
    }
}