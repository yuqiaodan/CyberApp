package com.tomato.cyber.dropline.course3Anim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class CourseThreeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(Modifier.safeDrawingPadding()) {
                AnimTestScreen()
            }
        }
    }
}

@Composable
fun AnimTestScreen() {
    var big by remember {
        mutableStateOf(false)
    }
    //animateXXXXAsState 内部调用了remember 不需要在外部再调用
    val sizeAnim by animateDpAsState(
        if (big) {
            96.dp
        } else {
            48.dp
        }
    )
    Box(
        Modifier
            .size(sizeAnim)
            .background(Color.Green)
            .clickable {
                big = !big
            })
}
