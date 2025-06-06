package com.tomato.cyber.dropline.course1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import com.tomato.cyber.R

class CourseOneActivity : ComponentActivity() {
    val path = "https://img1.baidu.com/it/u=745150868,1434104099&fm=253&fmt=auto&app=138&f=PNG?w=500&h=500"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //
        setContent {
            Column {
                //文本
                Text(text = "Hello Cyber")
                //本地图片
                Image(painterResource(R.drawable.ic_launcher_background),"")
                //网络图片
                Image(rememberAsyncImagePainter(path),"")
            }


        }
    }
}

