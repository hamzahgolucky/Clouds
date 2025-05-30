/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.cloudswithtile.presentation
import com.example.cloudswithtile.presentation.theme.MoodAddedScreen
import android.content.Context
import androidx.compose.foundation.Image
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyColumnDefaults
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import com.example.cloudswithtile.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.material.Text
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


class MainActivity : ComponentActivity() {
    private val heartRateViewModel: HeartRateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            BackgroundIMG()
            HomeScreen()
            //HeartRateScreen(heartRateViewModel)
            //MoodBarGraph()
        }
        heartRateViewModel.startListening()

        // Stop listening when the activity is destroyed
        lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                heartRateViewModel.stopListening()
            }
        })

    }
}

val allanFamily = FontFamily(
    Font(R.font.allan, FontWeight.Normal)
)
val cloudsfont2 = FontFamily(
    Font(R.font.cloudsfont3, FontWeight.Normal)
)
val Context.dataStore by preferencesDataStore(name = "settings")

suspend fun saveInt(context: Context, value: Int, INT_KEY: String) {
    val KEY = intPreferencesKey(INT_KEY)
    context.dataStore.edit { preferences ->
        preferences[KEY] = value
    }
}

fun readInt(context: Context, INT_KEY: String): Flow<Int> {
    val KEY = intPreferencesKey(INT_KEY)
    return context.dataStore.data.map { preferences ->
        preferences[KEY] ?: 0
    }
}


@Composable
fun BackgroundIMG(): Unit {
    val image2 = painterResource(R.drawable.backgroundgrid)
    Image(
        painter = image2,
        contentDescription = null,
        modifier = Modifier
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Cat1ImgList(navController: NavController) {
    val listState = rememberScalingLazyListState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    var velocity = 0f

    val moodItems = listOf(
        "Negative and Forceful",
        "Negative and Uncontrollable",
        "Negative Thoughts",
        "Negative and Passive",
        //"Agitation",
        "Positive and Lively",
        "Pleasant",
        "Positive Thoughts",
        "Quiet Positive",
        //"Reactive"
    )

    // Custom FlingBehavior with decay animation for smooth flinging
    //val flingBehavior = rememberSmoothFlingBehavior(listState)

    // Apply focus when the composable loads
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
    ) {
        ScalingLazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            //flingBehavior = flingBehavior,
            modifier = Modifier
                .fillMaxSize()
                .onRotaryScrollEvent {
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
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            scalingParams = ScalingLazyColumnDefaults.scalingParams(edgeScale = 0.5f, edgeAlpha = 0.02f),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            itemsIndexed(moodItems) { index, mood ->
                MoodItem(
                    mood = mood,
                    navController = navController,
                    context = context,
                    itemIndex = index + 1
                )
            }
        }
    }
}

@Composable
fun MoodItem(
    mood: String,
    navController: NavController,
    context: Context,
    itemIndex: Int
) {
    var index = itemIndex
    if (itemIndex > 4) { index = itemIndex + 1 }
    val coroutineScope = rememberCoroutineScope()
    val modifier = Modifier.clickable {
        coroutineScope.launch {
            saveInt(context, index, "cat1mood")
            navController.navigate("cat2img$index")
        }
    }

    when (itemIndex) {
        1 -> Cat1Img1(mood1 = mood, navController = navController, modifier = modifier)
        2 -> Cat1Img2(mood1 = mood, navController = navController, modifier = modifier)
        3 -> Cat1Img3(mood1 = mood, navController = navController, modifier = modifier)
        4 -> Cat1Img4(mood1 = mood, navController = navController, modifier = modifier)
        //5 -> Cat1Img5(mood1 = mood, navController = navController, modifier = modifier)
        5 -> Cat1Img6(mood1 = mood, navController = navController, modifier = modifier)
        6 -> Cat1Img7(mood1 = mood, navController = navController, modifier = modifier)
        7 -> Cat1Img8(mood1 = mood, navController = navController, modifier = modifier)
        8 -> Cat1Img9(mood1 = mood, navController = navController, modifier = modifier)
        //9 -> Cat1Img10(mood1 = mood, navController = navController, modifier = modifier)
    }
}



@Composable
fun Wearapp(navController: NavController) {
    val image = painterResource(R.drawable.entermoodtext)

    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.height(25.dp)
        )
    }
        Box(contentAlignment = Center) {
            Cat1ImgList(navController = navController)
        }
}


@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "first_Screen") {
        composable("first_Screen") {
            Wearapp(navController = navController)
        }
        composable("cat2img1") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                Cat2Img1(navController = navController)
                BackButton(
                    navController = navController, modifier = Modifier
                        .width(140.dp)
                        .height(35.dp)
                )
            }
        }
        composable("cat2img2") {
            Cat2Img2(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("cat2img3") {
            Cat2Img3(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("cat2img4") {
            Cat2Img4(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("cat2img5") {
            Cat2Img5(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("cat2img6") {
            Cat2Img6(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("cat2img7") {
            Cat2Img7(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("cat2img8") {
            Cat2Img8(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("cat2img9") {
            Cat2Img9(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("cat2img10") {
            Cat2Img10(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("finalentry11") {
            FinalEntry11(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
    }
        composable("finalentry22") {
            FinalEntry22(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("finalentry33") {
            FinalEntry33(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("finalentry44") {
            FinalEntry44(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("finalentry55") {
            FinalEntry55(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("finalentry66") {
            FinalEntry66(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("finalentry77") {
            FinalEntry77(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("finalentry88") {
            FinalEntry88(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("finalentry99") {
            FinalEntry99(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("finalentry1010") {
            FinalEntry1010(navController = navController)
            BackButton(
                navController = navController, modifier = Modifier
                    .width(140.dp)
                    .height(35.dp)
            )
        }
        composable("moodaddedscreen") {
            MoodAddedScreen(navController = navController)
        }
        composable("statsscreen") {
            StatOne(modifier =Modifier, navController = navController)
        }
        composable("chatgptscreen") {
            ChatGptScreen(navController = navController)
            CloudsButton(navController = navController, modifier = Modifier
                .width(140.dp)
                .height(35.dp))
        }
    }
}




