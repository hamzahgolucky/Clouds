package com.example.cloudswithtile.presentation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

fun numtomood2converter(num1:Int,num2:Int): String {
    var mood = ""
    if(num1==1){
        if (num2==1) {
            mood = "angry"
        } else if (num2==2) {
            mood = "annoyed"
        } else if (num2==3) {
            mood = "contempt"
        } else if (num2==4) {
            mood = "disgusted"
        }
    } else if(num1==2){
        if (num2==1) {
            mood = "anxiety"
        } else if (num2==2) {
            mood = "embarrassed"
        } else if (num2==3) {
            mood = "fearful"
        } else if (num2==4) {
            mood = "helpless"
        }
    } else if(num1==3) {
        if (num2==1) {
            mood = "doubt"
        } else if (num2==2) {
            mood = "envious"
        } else if (num2==3) {
            mood = "frustrated"
        } else if (num2==4) {
            mood = "guilty"
        } else if (num2==5) {
            mood = "shameful"
        }
    } else if(num1==4) {
        if (num2==1) {
            mood = "bored"
        } else if (num2==2) {
            mood = "despair"
        } else if (num2==3) {
            mood = "disappointed"
        } else if (num2==4) {
            mood = "hurt"
        } else {
            mood = "sad"
        }
    } else if(num1==5) {
        if (num2==1) {
            mood = "shocked"
        } else if (num2==2) {
            mood = "stressed"
        } else {
            mood = "tense"
        }
    } else if(num1==6) {
        if (num2==1) {
            mood = "amused"
        } else if (num2==2) {
            mood = "delighted"
        } else if (num2==3) {
            mood = "excited"
        } else if (num2==4) {
            mood = "happy"
        } else if (num2==5) {
            mood = "joyous"
        } else {
            mood = "pleased"
        }
    } else if(num1==7) {
        if (num2==1) {
            mood = "affection"
        } else if (num2==2) {
            mood = "empathy"
        } else if (num2==3) {
            mood = "friendly"
        } else if (num2==4) {
            mood = "love"
        }
    } else if(num1==8) {
        if (num2==1) {
            mood = "courageous"
        } else if (num2==2) {
            mood = "hopeful"
        } else if (num2==3) {
            mood = "proud"
        } else if (num2==4) {
            mood = "satisfied"
        } else {
            mood = "trustful"
        }
    } else if(num1==9) {
        if (num2==1) {
            mood = "calm"
        } else if (num2==2) {
            mood = "content"
        } else if (num2==3) {
            mood = "relaxed"
        } else if (num2==4) {
            mood = "relieved"
        } else {
            mood = "serene"
        }
    } else if(num1==10) {
        if (num2==1) {
            mood = "interest"
        } else if (num2==2) {
            mood = "politeness"
        } else {
            mood = "surprise"
        }
    }
    return mood
}

fun numtomoodconverter(num:Int): String {
    var mood = ""
    if(num==1){
        mood = "Negative and Forceful feelings"
    } else if(num==2){
        mood = "Negative and Uncontrollable feelings"
    } else if(num==3) {
        mood = "Negative Thoughts"
    } else if(num==4) {
        mood = "Negative and Passive feelings"
    } else if(num==5) {
        mood = "Agitation"
    } else if(num==6) {
        mood = "Positive and Lively feelings"
    } else if(num==7) {
        mood = "Pleasant feelings"
    } else if(num==8) {
        mood = "Positive Thoughts"
    } else if(num==9) {
        mood = "Quiet Positive feelings"
    } else if(num==10) {
        mood = "Reactive"
    }
    return mood
}



//private val Context.dataStore by preferencesDataStore("mood_preferences")


object MoodPreferences {
    val MOOD_LIST = stringPreferencesKey("mood_list")
}

fun serializeListToString(list: List<String>): String {
    return Gson().toJson(list)
}

fun deserializeStringToList(str: String): List<String> {
    return Gson().fromJson(str, Array<String>::class.java).toList()
}

suspend fun saveMood(context: Context, mood: String) {
    context.dataStore.edit { preferences ->
        val currentMoods = preferences[MoodPreferences.MOOD_LIST]?.let { deserializeStringToList(it) } ?: emptyList()
        val updatedMoods = currentMoods + mood
        preferences[MoodPreferences.MOOD_LIST] = serializeListToString(updatedMoods)
    }
}

fun getMoodList(context: Context): Flow<List<String>> {
    return context.dataStore.data.map { preferences ->
        preferences[MoodPreferences.MOOD_LIST]?.let { deserializeStringToList(it) } ?: emptyList()
    }
}

suspend fun clearMoodList(context: Context) {
    context.dataStore.edit { preferences ->
        preferences[MoodPreferences.MOOD_LIST] = serializeListToString(emptyList())
    }
}




class MoodViewModel : ViewModel() {
    private val _moodList = MutableStateFlow<List<String>>(emptyList())
    val moodList: StateFlow<List<String>> = _moodList

    fun refreshMoodList(context: Context) {
        viewModelScope.launch {
            getMoodList(context).collect { list ->
                _moodList.value = list
            }
        }
    }

    fun addMood(context: Context, mood: String) {
        viewModelScope.launch {
            saveMood(context, mood)
            // Instead of calling refreshMoodList here, directly update the _moodList
            _moodList.value = _moodList.value + mood
        }
    }

    fun clearMoods(context: Context) {
        viewModelScope.launch {
            clearMoodList(context)
            _moodList.value = emptyList()
        }
    }
}






@Composable
fun MoodEntryScreen(viewModel: MoodViewModel = viewModel(), moodinput:String) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val moodList by viewModel.moodList.collectAsState()
    var moodstring by remember {
        mutableStateOf(moodinput)
    }
    Log.d("the input mood", moodstring)
    Column(modifier = Modifier.padding(16.dp)) {

        if (moodstring.isNotEmpty()) {
            viewModel.addMood(context, moodstring)
            moodstring = ""
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Moods collected so far:")
        Button(onClick = {
            scope.launch {
                viewModel.clearMoods(context)
            }
        }) {
            Text("Clear Moods")
        }
        Spacer(modifier = Modifier.height(8.dp))
        var i = 0

        moodList.forEach { mood ->

            Log.d("mood",mood)
            if(i!=moodList.size-1){
                Text(text = mood)
                i+=1
            } else {
                i += 1
            }

        }
        Log.d("mood list fr",moodList.toString())
        Spacer(modifier = Modifier.height(16.dp))


    }

    LaunchedEffect(Unit) {
        viewModel.refreshMoodList(context)
    }
}