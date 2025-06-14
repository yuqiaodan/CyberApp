package com.tomato.cyber.dropline.course4modifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


class CourseFourActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(Modifier
                .safeDrawingPadding()
                .fillMaxSize()) {
                CourseFourScreen(Modifier.background(Color.Blue))
            }
        }
    }
}

@Composable
fun CourseFourScreen(modifier: Modifier = Modifier) {
    Box(modifier.size(180.dp).clickable { println("111") }.size(50.dp).clickable { println("222") })
}
