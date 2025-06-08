package com.tomato.cyber.dropline.course2state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView


val LocalActivity = compositionLocalOf<ComponentActivity> { error("LocalActivity 没有提供值") }
class CourseTwoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalContext
            LocalView
            LocalDensity
            CompositionLocalProvider(LocalActivity provides this) {
                LocalActivity.current
                Box(Modifier.safeDrawingPadding()) {
                    CompositionLocalTest()
                }
            }
        }
    }
}


//compositionLocal 要求使用local开头命名对象,全局可用首字母大写，局部可用首字母小写 若没有赋值就调用则使用error抛出异常
val LocalName = compositionLocalOf<String> { error("LocalName 没有提供值") }

//不使用error抛出异常 也可以提供默认值 不经常变化或者不变化的值 使用staticCompositionLocalOf
val LocalAge = staticCompositionLocalOf<Int> { 0 }


@Composable
fun CompositionLocalTest() {
    Column {
        // LocalName provides "yuqiaodan" 这里的provides是中缀函数 完全等价于:LocalName.provides("yuqiaodan")
        var name1 by remember { mutableStateOf("yuqiaodan") }
        CompositionLocalProvider(LocalName provides name1) {
            //CompositionLocalProvider的提供是可以嵌套覆盖的
            CompositionLocalProvider(LocalName provides "xiaoxiong"){
                UserText()
            }
            UserText()
        }
        //CompositionLocalProvider也可以添加多个参数
        CompositionLocalProvider(LocalName provides "huihui", LocalAge provides 25) {
            UserText()
        }
        //在CompositionLocalProvider作用域外部调用UserText 且在其内部调用LocalName.current 则执行LocalName compositionLocalOf中的代码抛出异常
        //UserText()
        //可以在外部修改name1 ，提供name1的CompositionLocalProvider作用域内部的所有用到LocalName的可组合函数都会刷新
        Button({
            name1 = "qiaodan"
        }) { }
    }
}

@Composable
fun UserText() {
    //CompositionLocal可以穿透其作用域内部的所有函数
    Text(LocalName.current)
}




