package com.tomato.cyber.dropline.course4modifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.tomato.cyber.log
import kotlin.math.min


class CourseFourActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(
                Modifier
                    .safeDrawingPadding()
                    .fillMaxSize()
            ) {
                PointerInputModifierTest()
            }
        }
    }
}

@Composable
fun PointerInputModifierTest() {


    Box( //可实现：普通点击、长按点击、双击
        Modifier
            .pointerInput(Unit) {

                awaitEachGesture{

                }

                awaitPointerEventScope {
                    val down = awaitFirstDown()

                }

                detectTapGestures(
                    //普通点击
                    onTap = {
                        log("点击")
                    },
                    //长按点击
                    onLongPress = {
                        log("长按")
                    },
                    //双击
                    onDoubleTap = {
                        log("双击")
                    },
                    //触摸（按下）
                    onPress = {
                        log("触摸（按下）")
                    }
                )
            }
            .size(100.dp)
            .background(Color.Red))



  /*  Box( //可实现：普通点击、长按点击、双击
        Modifier
            .combinedClickable(
                //普通点击
                onClick = {
                    log("点击")
                },
                //长按点击
                onLongClick = {
                    log("长按")
                },
                //双击
                onDoubleClick = {
                    log("双击")
                }
            )
            .size(100.dp)
            .background(Color.Red))*/


}

@Composable
fun DrawModifierTest() {
    Text(
        "yuqiaodan", Modifier
            .background(Color.Green)
            .drawWithContent {
                //右边位置绘制一个圆
                drawCircle(Color.Red, radius = 5.dp.toPx(), center = Offset(size.width, size.height / 2))
                //绘制原有的绘制内容 也就是drawWithContent右边的绘制内容 和 组件原本的内容
                drawContent()
                //如果不调用 drawContent()，那么组件的绘制内容就不会被绘制 无内容
                //drawContent()
                //中间位置绘制一个圆
                drawCircle(Color.Red, radius = 5.dp.toPx())
            }
            //drawWithContent右边的绘制内容会受到drawContent影响
            .padding(10.dp)
            .background(Color.Yellow))
}


@Composable
fun LayoutModifierTest() {
    Box(Modifier.background(Color.Yellow)) {
        Text("yuqiaodan", Modifier.layout { measurable, constraints ->
            //constraints是父布局对改组件的限制，可以通过constraints设置一个padding
            //比如这里想添加一个2dp的padding,那么就需要将constraints.maxWidth和constraints.maxHeight减去2创建一个newConstraints
            /* val padding = 2.dp.roundToPx()
             val newConstraints = constraints.copy(
                 maxWidth = constraints.maxWidth - padding,
                 maxHeight = constraints.maxWidth - padding
             )*/

            //测量:使用measurable.measure测量自身的尺寸
            val placeable = measurable.measure(constraints)
            //修改1:比如 希望修改组件尺寸 取宽高的最小值设置为正方形
            val size = min(placeable.width, placeable.height)
            //布局:Modifier.layout要求返回一个MeasureResult 通过layout()方法返回
            //布局:layout()方法返回需要传入测量结果进行保存，并且在placementBlock中确认组件的摆放位置
            return@layout layout(size, size, placementBlock = {
                //需要在placementBlock中通过placeable.placeRelative确认组件的摆放位置
                //修改2:比如 希望修改组件摆放位置偏移 x轴向右偏移10px y轴向下偏移5dp
                placeable.placeRelative(10, 5.dp.roundToPx())
            })
        })
    }
}

@Composable
fun LayoutModifierTest2() {
    Modifier.padding(5.dp)
    Box(Modifier.background(Color.Yellow)) {
        Text("yuqiaodan", Modifier.layout { measurable, constraints ->
            //constraints是父布局对改组件的限制,其内部包含了对该组件的宽高上下限限制，可以通过constraints设置一个padding
            //修改3：比如这里想添加一个5dp的padding,那么就需要将constraints.maxWidth和constraints.maxHeight减去2创建一个newConstraints
            val paddingPx = 5.dp.roundToPx()
            val newConstraints = constraints.copy(
                maxWidth = constraints.maxWidth - paddingPx * 2,
                maxHeight = constraints.maxWidth - paddingPx * 2
            )
            //测量:使用measurable.measure测量自身的尺寸
            val placeable = measurable.measure(newConstraints)
            //布局:Modifier.layout要求返回一个MeasureResult 通过layout()方法返回
            //布局:layout()方法返回需要传入测量结果进行保存，并且在placementBlock中确认组件的摆放位置
            return@layout layout(
                placeable.width + paddingPx * 2,
                placeable.height + paddingPx * 2,
                placementBlock = {
                    //需要在placementBlock中通过placeable.placeRelative确认组件的摆放位置
                    placeable.placeRelative(paddingPx, paddingPx)
                })
        })
    }
}
