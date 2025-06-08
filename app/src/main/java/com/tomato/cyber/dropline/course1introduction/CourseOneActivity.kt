package com.tomato.cyber.dropline.course1introduction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.tomato.cyber.R

class CourseOneActivity : ComponentActivity() {
    val path =
        "https://img1.baidu.com/it/u=745150868,1434104099&fm=253&fmt=auto&app=138&f=PNG?w=500&h=500"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Cursor1Content()
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun Cursor1Content(modifier: Modifier = Modifier) {
        Column(Modifier.safeDrawingPadding()) {
            //文本
            Text(text = "Hello Cyber")
            //本地图片
            Image(painterResource(R.drawable.ic_launcher_background), "")
            //网络图片
            Image(rememberAsyncImagePainter(path), "", Modifier.size(80.dp))
            Box(
                Modifier
                    .padding(8.dp)
                    .clickable { }
                    .background(Color.Blue)
                    .padding(8.dp)) {
                Text(text = "Hello Cyber", fontSize = 18.sp, color = Color.White)
            }
            Button(onClick = { }) {
                Text(text = "Hello Cyber")
            }
        }
    }
}

