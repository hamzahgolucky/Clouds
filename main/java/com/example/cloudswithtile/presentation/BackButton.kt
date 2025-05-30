package com.example.cloudswithtile.presentation

import androidx.compose.foundation.layout.Arrangement.Bottom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme.colors
import androidx.wear.compose.material.Text
import com.example.cloudswithtile.presentation.cloudsfont2


@Composable
fun BackButton(navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(onClick = { navController.popBackStack() }, modifier = modifier, colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)) {
            Text("back", fontFamily = cloudsfont2, color = Color.LightGray)
        }
    }
}